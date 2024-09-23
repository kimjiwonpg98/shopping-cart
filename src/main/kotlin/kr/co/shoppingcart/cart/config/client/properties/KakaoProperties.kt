package kr.co.shoppingcart.cart.config.client.properties

import kr.co.shoppingcart.cart.config.client.properties.KakaoProperties.Companion.KAKAO_PROPERTIES
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = KAKAO_PROPERTIES)
class KakaoProperties(
    private val baseurl: String,
    private val clientId: String,
    private val kakaoRedirectUrl: String,
) {
    fun getBaseUrl(): String = baseurl

    fun getClientId(): String = clientId

    fun getKakaoRedirectUrl(): String = kakaoRedirectUrl

    companion object {
        const val KAKAO_PROPERTIES = "client.kakao"
    }
}
