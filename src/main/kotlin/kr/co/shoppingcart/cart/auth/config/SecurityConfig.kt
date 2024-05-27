package kr.co.shoppingcart.cart.auth.config

import kr.co.shoppingcart.cart.auth.JwtFilter
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
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtFilter: JwtFilter,
    private val entryPoint: AuthenticationEntryPoint,
) {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .httpBasic { obj: HttpBasicConfigurer<HttpSecurity> -> obj.disable() }
            .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
            .cors { obj: CorsConfigurer<HttpSecurity> -> obj.disable() }
            .authorizeHttpRequests { requests ->
                requests.requestMatchers(HttpMethod.POST,"/login").permitAll()
                requests.requestMatchers(HttpMethod.POST, "/cart").authenticated()
            }
            .sessionManagement { sessionManagement: SessionManagementConfigurer<HttpSecurity?> ->
                sessionManagement.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .addFilterBefore(jwtFilter, BasicAuthenticationFilter::class.java)
            .exceptionHandling { it.authenticationEntryPoint(entryPoint) }
            .build()
    }
}