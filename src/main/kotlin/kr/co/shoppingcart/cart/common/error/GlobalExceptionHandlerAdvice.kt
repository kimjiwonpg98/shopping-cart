package kr.co.shoppingcart.cart.common.error

import jakarta.persistence.EntityNotFoundException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.common.error.model.ExceptionResponseBody
import kr.co.shoppingcart.cart.common.error.translators.ExceptionCodeTranslator
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandlerAdvice(
    private val translator: ExceptionCodeTranslator,
) {
    @ExceptionHandler(value = [CustomException::class])
    fun apiException(error: CustomException): ResponseEntity<ExceptionResponseBody> {
        logger.error {
            "[API] Error - message: ${error.message} cause: ${error.cause} detail: ${error.detailInformation}"
        }
        when {
            error.detailInformation != null -> {
                val errorBody = translator.translate(error.code, error.detailInformation)
                return ResponseEntity.status(error.httpStatus).body(errorBody)
            }
            else -> {
                val errorBody = translator.translate(error.code)
                return ResponseEntity.status(error.httpStatus).body(errorBody)
            }
        }
    }

    @ExceptionHandler(value = [EntityNotFoundException::class])
    fun handleEntityNotFoundException(error: EntityNotFoundException): ResponseEntity<ExceptionResponseBody> {
        logger.error { "[Unintended] Error - message: ${error.message} cause: ${error.cause}" }
        val errorBody = translator.translate(ExceptionCode.E_404_000)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody)
    }

    @ExceptionHandler(value = [RuntimeException::class])
    fun handleRuntimeException(error: RuntimeException): ResponseEntity<ExceptionResponseBody> {
        logger.error { "[Unintended] Error - message: ${error.message} cause: ${error.cause}" }

        val errorBody = translator.translate(ExceptionCode.E_400_000)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody)
    }

    // 의도하지 않은 에러
    @ExceptionHandler(value = [Exception::class])
    fun handleException(error: Exception): ResponseEntity<ExceptionResponseBody> {
        logger.error { "[Unintended] Error - message: ${error.message} cause: ${error.cause}" }

        val errorBody = translator.translate(ExceptionCode.E_500_000)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody)
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}
