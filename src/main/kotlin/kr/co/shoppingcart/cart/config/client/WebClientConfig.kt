package kr.co.shoppingcart.cart.config.client

import kr.co.shoppingcart.cart.config.client.properties.KakaoProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableConfigurationProperties(KakaoProperties::class)
class WebClientConfig(
    private val kakaoProperties: KakaoProperties,
) {
    @Bean(name = [KAKAO_WEB_CLIENT])
    fun kakaoWebClient(): WebClient = WebClient.builder().baseUrl(kakaoProperties.getBaseUrl()).build()

    companion object {
        const val KAKAO_WEB_CLIENT = "kakaoWebClient"
    }
}
