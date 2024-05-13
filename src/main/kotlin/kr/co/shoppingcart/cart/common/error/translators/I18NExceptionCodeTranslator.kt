package kr.co.shoppingcart.cart.common.error.translators

import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.common.error.model.ExceptionResponseBody
import mu.KotlinLogging
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component

@Component
class I18NExceptionCodeTranslator (
    private val messageSource: MessageSource
): ExceptionCodeTranslator {
    override fun translate(key: ExceptionCode): ExceptionResponseBody =
        ExceptionResponseBody(
            code = translate("${key.name}.code"),
            message = translate("${key.name}.message"),
            title = translate("${key.name}.title"),
        )

    private fun translate(code: String): String {
        return try {
            messageSource.getMessage(code, null, LocaleContextHolder.getLocale())
        } catch (exception: Exception) {
            logger.warn { "Exception occurred while translating: $code" }
            return ""
        }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}