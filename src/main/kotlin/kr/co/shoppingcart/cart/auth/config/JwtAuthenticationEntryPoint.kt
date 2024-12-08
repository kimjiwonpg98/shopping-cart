package kr.co.shoppingcart.cart.auth.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.co.shoppingcart.cart.auth.enums.JwtExceptionEnum
import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    @Throws(CustomException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        val exception = request.getAttribute("exception")

        if (exception == null) {
            setResponse(response, ExceptionCode.E_401_000)
        }

        if (exception.equals(JwtExceptionEnum.MALFORMED.name)) {
            setResponse(response, ExceptionCode.E_401_000)
        }
        if (exception.equals(JwtExceptionEnum.SIGNATURE.name)) {
            setResponse(response, ExceptionCode.E_401_000)
        }
        if (exception.equals(JwtExceptionEnum.EXPIRED.name)) {
            setResponse(response, ExceptionCode.E_401_001)
        }
        if (exception.equals(JwtExceptionEnum.EMPTY.name)) {
            setResponse(response, ExceptionCode.E_401_000)
        }
    }

    private fun setResponse(
        response: HttpServletResponse,
        code: ExceptionCode,
    ) {
        response.contentType = "application/json;charset=UTF-8"
        response.status = code.httpStatus.value()

        val responseJson = CustomException
        responseJson.responseBody(code)

        response.writer.print(responseJson)
    }
}
