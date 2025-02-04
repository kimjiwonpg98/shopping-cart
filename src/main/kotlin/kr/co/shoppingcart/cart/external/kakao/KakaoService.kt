package kr.co.shoppingcart.cart.external.kakao

import kr.co.shoppingcart.cart.config.client.properties.KakaoProperties
import kr.co.shoppingcart.cart.external.kakao.request.GetUserInfoByAuthTokenRequestQueryParams
import kr.co.shoppingcart.cart.external.kakao.request.IssueTokenRequestBody
import kr.co.shoppingcart.cart.external.kakao.request.UnlinkUserRequestBody
import kr.co.shoppingcart.cart.external.kakao.response.GetKakaoUserByAuthTokenResponse
import kr.co.shoppingcart.cart.external.kakao.response.IssueTokenResponseBody
import kr.co.shoppingcart.cart.external.kakao.response.UnlinkUserResponseBody
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class KakaoService(
    @Qualifier("kakaoWebClient")
    private val kakaoWebClient: WebClient,
    @Qualifier("kakaoAuthWebClient")
    private val kakaoAuthWebClient: WebClient,
    private val kakaoProperties: KakaoProperties,
) : KakaoClient {
    override fun getTokenByGrantCode(grantCode: String): String? {
        val data =
            IssueTokenRequestBody(
                clientId = kakaoProperties.getClientId(),
                grantType = AUTHORIZATION_CODE,
                redirectUrl = kakaoProperties.getKakaoRedirectUrl(),
                code = grantCode,
            ).toFormDataRequestBody()

        val response =
            kakaoAuthWebClient
                .post()
                .uri {
                    it.path(GET_TOKEN_URI).build()
                }.accept(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(data)
                .retrieve()
                .bodyToMono<IssueTokenResponseBody>()
                .doOnError { e ->
                    logger.warn { e.cause }
                }.block()

        return response?.access_token
    }

    override fun getUserByAuthToken(authToken: String): GetKakaoUserByAuthTokenResponse? {
        val query =
            GetUserInfoByAuthTokenRequestQueryParams(
                propertiesKey = listOf("kakao_account.name", "kakao_account.email", "kakao_account.gender"),
            ).toQueryParams()

        val response =
            kakaoWebClient
                .get()
                .uri {
                    it.path(GET_USER_INFO).queryParams(query).build()
                }.accept(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer $authToken")
                .retrieve()
                .bodyToMono<GetKakaoUserByAuthTokenResponse>()
                .doOnError { e ->
                    logger.warn { e.cause }
                }.block()

        return response
    }

    override fun unlinkUser(authIdentifier: String): Long? {
        val data =
            UnlinkUserRequestBody(
                authIdentifier = authIdentifier.toLong(),
            ).toFormDataRequestBody()

        val response =
            kakaoWebClient
                .post()
                .uri {
                    it.path(POST_USER_UNLINK).build()
                }.accept(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.AUTHORIZATION, "KakaoAK ${kakaoProperties.getAdminKey()}")
                .bodyValue(data)
                .retrieve()
                .bodyToMono<UnlinkUserResponseBody>()
                .doOnError { e ->
                    logger.warn { e.cause }
                }.block()

        return response?.id
    }

    companion object {
        private const val GET_TOKEN_URI = "/oauth/token"
        private const val GET_USER_INFO = "/v2/user/me"
        private const val POST_USER_UNLINK = "/v1/user/unlink"
        private const val AUTHORIZATION_CODE = "authorization_code"

        private val logger = KotlinLogging.logger {}
    }
}

// redirect
// .retrieve()
// .toEntity(String::class.java)
// .flatMap { responseEntity ->
//    if (responseEntity.statusCode.is3xxRedirection) {
//        val redirectUrl = responseEntity.headers.location.toString()
//        Mono.just(redirectUrl)
//    } else {
//        Mono.error(Throwable("Redirect expected but not received"))
//    }
// }.block()
