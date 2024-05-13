package kr.co.shoppingcart.cart.common.error.model

import org.springframework.http.HttpStatus

enum class ExceptionCode (
    val code: Int,
    val httpStatus: HttpStatus,
) {

    E_400_000(400, HttpStatus.BAD_REQUEST),

    /* 토큰 검증 후 오류 */
    E_401_000(401, HttpStatus.UNAUTHORIZED),

    E_500_000(500, HttpStatus.INTERNAL_SERVER_ERROR),

}