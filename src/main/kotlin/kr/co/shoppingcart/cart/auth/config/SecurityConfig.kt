package kr.co.shoppingcart.cart.auth.config

import kr.co.shoppingcart.cart.auth.CustomFailHandler
import kr.co.shoppingcart.cart.auth.CustomSuccessHandler
import kr.co.shoppingcart.cart.auth.JwtFilter
import kr.co.shoppingcart.cart.auth.enums.TokenInformationEnum
import kr.co.shoppingcart.cart.external.common.Oauth2Service
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtFilter: JwtFilter,
    private val entryPoint: JwtAuthenticationEntryPoint,
    private val oauth2Service: Oauth2Service,
    private val customSuccessHandler: CustomSuccessHandler,
    private val customFailHandler: CustomFailHandler,
) {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain =
        httpSecurity
            .httpBasic { obj: HttpBasicConfigurer<HttpSecurity> -> obj.disable() }
            .csrf { obj: CsrfConfigurer<HttpSecurity> ->
                obj.disable()
            }.cors { cors ->
                cors.configurationSource { request ->
                    org.springframework.web.cors.CorsConfiguration().apply {
                        allowedOrigins = listOf("http://localhost:3000", "https://kka-dam.vercel.app") // 허용할 도메인
                        allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                        allowedHeaders = listOf("Authorization", "Content-Type") // 허용할 헤더
                        allowCredentials = true // 쿠키 및 인증 정보 허용 여부
                    }
                }
            }.oauth2Login { oauth2Configurer ->
                oauth2Configurer
                    .userInfoEndpoint { userInfoEndpoint ->
                        userInfoEndpoint.userService(oauth2Service)
                    }.successHandler(customSuccessHandler)
                    .failureHandler(customFailHandler)
                    // TODO: 실패 시 redirect 페이지
                    .failureUrl("https://kka-dam.vercel.app")
            }.authorizeHttpRequests { requests ->
                requests.requestMatchers(HttpMethod.GET, "/v1/search").permitAll()
                requests.requestMatchers(HttpMethod.GET, "/login/oauth2/code").permitAll()
                requests.requestMatchers(HttpMethod.GET, "/v1").hasAnyRole(TokenInformationEnum.USER.name)
                requests.requestMatchers(HttpMethod.POST, "/v1").hasAnyRole(TokenInformationEnum.USER.name)
                requests.requestMatchers(HttpMethod.DELETE, "/v1").hasAnyRole(TokenInformationEnum.USER.name)
                requests.requestMatchers(HttpMethod.PATCH, "/v1").hasAnyRole(TokenInformationEnum.USER.name)
                requests.anyRequest().permitAll()
            }.sessionManagement { sessionManagement: SessionManagementConfigurer<HttpSecurity?> ->
                sessionManagement.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS,
                )
            }.addFilterBefore(jwtFilter, BasicAuthenticationFilter::class.java)
            .exceptionHandling { it.authenticationEntryPoint(entryPoint) }
            .build()
}
