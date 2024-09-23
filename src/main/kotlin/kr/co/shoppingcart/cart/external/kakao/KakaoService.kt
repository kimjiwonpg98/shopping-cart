package kr.co.shoppingcart.cart.external.kakao

import kr.co.shoppingcart.cart.config.client.properties.KakaoProperties
import kr.co.shoppingcart.cart.external.ThirdPartyLoginClient
import kr.co.shoppingcart.cart.external.kakao.request.IssueTokenRequestBody
import kr.co.shoppingcart.cart.external.kakao.response.IssueTokenResponseBody
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class KakaoService(
    @Qualifier("kakaoWebClient")
    private val kakaoWebClient: WebClient,
    private val kakaoProperties: KakaoProperties,
) : ThirdPartyLoginClient {
    override fun getTokenByGrantCode(grantCode: String): String? {
        val data =
            IssueTokenRequestBody(
                clientId = kakaoProperties.getClientId(),
                grantType = AUTHORIZATION_CODE,
                redirectUrl = kakaoProperties.getKakaoRedirectUrl(),
                code = grantCode,
            ).toFormDataRequestBody()

        val response =
            kakaoWebClient
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

    companion object {
        private const val GET_TOKEN_URI = "/oauth/token"
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
