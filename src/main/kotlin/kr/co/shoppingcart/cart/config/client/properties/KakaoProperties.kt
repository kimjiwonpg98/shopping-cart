package kr.co.shoppingcart.cart.config.client.properties

import kr.co.shoppingcart.cart.config.client.properties.KakaoProperties.Companion.KAKAO_PROPERTIES
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = KAKAO_PROPERTIES)
class KakaoProperties(
    private val baseUrl: String,
    private val authBaseUrl: String,
    private val clientId: String,
    private val kakaoRedirectUrl: String,
    private val adminKey: String,
) {
    fun getBaseUrl(): String = baseUrl

    fun getAuthBaseUrl(): String = authBaseUrl

    fun getClientId(): String = clientId

    fun getKakaoRedirectUrl(): String = kakaoRedirectUrl

    fun getAdminKey(): String = adminKey

    companion object {
        const val KAKAO_PROPERTIES = "client.kakao"
    }
}
