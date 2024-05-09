package kr.co.shoppingcart.common.error

import kr.co.shoppingcart.common.error.model.ExceptionCode
import kr.co.shoppingcart.common.error.model.ExceptionResponseBody
import kr.co.shoppingcart.common.error.translators.ExceptionCodeTranslator
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandlerAdvice (
  private val translator: ExceptionCodeTranslator
) {
    @ExceptionHandler(CustomException::class)
    fun apiException(error: CustomException): ResponseEntity<ExceptionResponseBody> {
        logger.error { error.cause }

        val errorBody = translator.translate(error.code)
        return ResponseEntity.status(error.httpStatus).body(errorBody)
    }

    /* 의도하지 않은 에러 */
    @ExceptionHandler(Exception::class)
    fun handleException(error: Exception): ResponseEntity<ExceptionResponseBody> {
        logger.error { "[Unintended] Error - message: ${error.message} cause: ${error.cause}" }

        val errorBody = translator.translate(ExceptionCode.E_500_000)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody)
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}