package kr.co.shoppingcart.cart.auth.config

import kr.co.shoppingcart.cart.auth.CustomFailHandler
import kr.co.shoppingcart.cart.auth.CustomSuccessHandler
import kr.co.shoppingcart.cart.auth.JwtExceptionFilter
import kr.co.shoppingcart.cart.auth.JwtFilter
import kr.co.shoppingcart.cart.auth.enums.TokenInformationEnum
import kr.co.shoppingcart.cart.external.common.Oauth2Service
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtFilter: JwtFilter,
    private val jwtExceptionFilter: JwtExceptionFilter,
    private val entryPoint: JwtAuthenticationEntryPoint,
    private val oauth2Service: Oauth2Service,
    private val customSuccessHandler: CustomSuccessHandler,
    private val customFailHandler: CustomFailHandler,
) {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(
        httpSecurity: HttpSecurity,
        jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    ): SecurityFilterChain =
        httpSecurity
            .httpBasic { obj: HttpBasicConfigurer<HttpSecurity> -> obj.disable() }
            .csrf { obj: CsrfConfigurer<HttpSecurity> ->
                obj.disable()
            }.cors { obj: CorsConfigurer<HttpSecurity> -> obj.disable() }
            .oauth2Login { oauth2Configurer ->
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
                requests.requestMatchers(HttpMethod.GET, "/ws/**").permitAll()
                requests.requestMatchers(HttpMethod.GET, "/v1").hasAnyRole(TokenInformationEnum.USER.name)
                requests.requestMatchers(HttpMethod.POST, "/v1").hasAnyRole(TokenInformationEnum.USER.name)
                requests.requestMatchers(HttpMethod.DELETE, "/v1").hasAnyRole(TokenInformationEnum.USER.name)
                requests.requestMatchers(HttpMethod.PATCH, "/v1").hasAnyRole(TokenInformationEnum.USER.name)
                requests.anyRequest().permitAll()
            }.sessionManagement { sessionManagement: SessionManagementConfigurer<HttpSecurity?> ->
                sessionManagement.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS,
                )
            }.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(jwtExceptionFilter, JwtFilter::class.java)
            .exceptionHandling { it.authenticationEntryPoint(entryPoint) }
            .build()
}
