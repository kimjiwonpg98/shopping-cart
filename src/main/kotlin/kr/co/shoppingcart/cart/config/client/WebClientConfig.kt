package kr.co.shoppingcart.cart.config.client

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {
    @Bean(name = [KAKAO_WEB_CLIENT])
    fun kakaoWebClient(): WebClient = WebClient.builder().baseUrl(KAKAO_BASE_URL).build()

    companion object {
        const val KAKAO_BASE_URL = "https://kauth.kakao.com"
        const val KAKAO_WEB_CLIENT = "kakaoWebClient"
    }
}
