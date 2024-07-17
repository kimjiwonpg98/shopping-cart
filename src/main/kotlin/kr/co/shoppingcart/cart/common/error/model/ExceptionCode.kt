package kr.co.shoppingcart.cart.common.error.model

import org.springframework.http.HttpStatus

enum class ExceptionCode(
    val code: Int,
    val httpStatus: HttpStatus,
) {
    E_400_000(400_000, HttpStatus.BAD_REQUEST),

    // 토큰 검증 후 오류
    E_401_000(401_000, HttpStatus.UNAUTHORIZED),
    E_403_000(403_000, HttpStatus.FORBIDDEN),
    E_403_001(403_001, HttpStatus.FORBIDDEN),

    E_404_000(404_000, HttpStatus.NOT_FOUND),
    E_404_001(404_001, HttpStatus.NOT_FOUND),

    E_500_000(500_000, HttpStatus.INTERNAL_SERVER_ERROR),
}
