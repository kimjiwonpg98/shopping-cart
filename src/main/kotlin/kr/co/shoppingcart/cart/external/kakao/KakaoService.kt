package kr.co.shoppingcart.cart.external.kakao

import kr.co.shoppingcart.cart.external.ThirdPartyLoginClient
import kr.co.shoppingcart.cart.external.kakao.request.GetGrantCodeRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class KakaoService(
    @Value("\${kakao.client-id}")
    private val clientId: String,
    @Qualifier("kakaoWebClient")
    private val kakaoWebClient: WebClient,
) : ThirdPartyLoginClient {
    override fun getGrantCode(): String? {
        val query =
            GetGrantCodeRequest(
                clientId = clientId,
                redirectUri = "https://google.com",
                scope = listOf("account_email"),
            ).toQueryParams()

        return kakaoWebClient.get()
            .uri {
                it.path(GET_GRANT_CODE_URI).queryParams(query).build()
            }
            .accept(MediaType.APPLICATION_FORM_URLENCODED)
            .retrieve()
            .toEntity(String::class.java)
            .flatMap { responseEntity ->
                if (responseEntity.statusCode.is3xxRedirection) {
                    val redirectUrl = responseEntity.headers.location.toString()
                    Mono.just(redirectUrl)
                } else {
                    Mono.error(Throwable("Redirect expected but not received"))
                }
            }
            .block()
    }

    companion object {
        private const val GET_GRANT_CODE_URI = "https://kauth.kakao.com/oauth/authorize"
    }
}
