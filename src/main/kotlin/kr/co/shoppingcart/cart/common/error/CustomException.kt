package kr.co.shoppingcart.cart.common.error

import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import org.springframework.http.HttpStatus

class CustomException private constructor(
    message: String,
    cause: Throwable? = null,
    val code: ExceptionCode = ExceptionCode.E_500_000,
    val httpStatus: HttpStatus = code.httpStatus,
    val detailInformation: String? = null
    ) : Exception(message, cause) {
    companion object {
        private fun fromErrorCode(
            code: ExceptionCode,
            cause: Throwable? = null,
            detailInformation: String? = null
        ): CustomException = CustomException (
            code = code,
            message = "name=${code.code}, status=${code.httpStatus.value()}",
            cause = cause,
            detailInformation = detailInformation
        )

        fun responseBody(
            code: ExceptionCode,
            cause: Throwable? = null,
            detailInformation: String? = null
        ): CustomException = fromErrorCode(
            code = code,
            cause = cause,
            detailInformation = detailInformation
        )
    }
}