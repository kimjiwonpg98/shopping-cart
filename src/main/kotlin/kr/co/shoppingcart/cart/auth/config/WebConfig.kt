package kr.co.shoppingcart.cart.auth.config

import kr.co.shoppingcart.cart.auth.AuthAccountResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val authAccountResolver: AuthAccountResolver,
): WebMvcConfigurer {
    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(authAccountResolver)
    }
}