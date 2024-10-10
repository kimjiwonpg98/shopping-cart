package kr.co.shoppingcart.cart.auth.config

import kr.co.shoppingcart.cart.auth.JwtFilter
import kr.co.shoppingcart.cart.auth.enums.TokenInformationEnum
import kr.co.shoppingcart.cart.external.common.Oauth2Service
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtFilter: JwtFilter,
    private val entryPoint: AuthenticationEntryPoint,
    private val oauth2Service: Oauth2Service,
) {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain =
        httpSecurity
            .httpBasic { obj: HttpBasicConfigurer<HttpSecurity> -> obj.disable() }
            .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
            .cors { obj: CorsConfigurer<HttpSecurity> -> obj.disable() }
            .authorizeHttpRequests { requests ->
                requests.requestMatchers(HttpMethod.GET, "/v1/search").permitAll()
                requests.requestMatchers(HttpMethod.POST, "/v1").hasAnyRole(TokenInformationEnum.USER.name)
                requests.requestMatchers(HttpMethod.DELETE, "/v1").hasAnyRole(TokenInformationEnum.USER.name)
                requests.requestMatchers(HttpMethod.PATCH, "/v1").hasAnyRole(TokenInformationEnum.USER.name)
            }.oauth2Login { oauth2Configurer ->
                oauth2Configurer
//                    .loginPage("http://localhost:3000/login") // 메인 페이지로 redirect
                    .userInfoEndpoint { userInfoEndpoint ->
                        userInfoEndpoint.userService(oauth2Service)
                    }.successHandler { _, response, _ ->
                        response.contentType = MediaType.APPLICATION_JSON_VALUE
                        response.writer.write("{\"success\": true}")
                    }
            }.sessionManagement { sessionManagement: SessionManagementConfigurer<HttpSecurity?> ->
                sessionManagement.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS,
                )
            }.addFilterBefore(jwtFilter, BasicAuthenticationFilter::class.java)
            .exceptionHandling { it.authenticationEntryPoint(entryPoint) }
            .build()
}

// @Bean
// class CustomSuccessHandler : AuthenticationSuccessHandler {
//    override fun onAuthenticationSuccess(
//        request: HttpServletRequest?,
//        response: HttpServletResponse,
//        authentication: Authentication,
//    ) {
//        response.contentType = MediaType.APPLICATION_JSON_VALUE
//        response.writer.write("{\"success\": true}")
//    }
// }
