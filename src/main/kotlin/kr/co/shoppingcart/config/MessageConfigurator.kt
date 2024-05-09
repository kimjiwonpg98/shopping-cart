package kr.co.shoppingcart.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*

@Configuration
class MessageConfigurator: WebMvcConfigurer {
    @Bean
    fun localeResolver(): SessionLocaleResolver = SessionLocaleResolver().apply {
        setDefaultLocale(Locale.KOREA)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LocaleChangeInterceptor().apply { paramName = "lang" })
    }
}