package kr.co.shoppingcart.cart.auth

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.common.error.translators.ExceptionCodeTranslator
import net.minidev.json.JSONObject
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.security.SignatureException

@Component
class JwtExceptionFilter(
    val translator: ExceptionCodeTranslator,
) : OncePerRequestFilter() {
    @Throws(ServletException::class, AuthenticationException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: MalformedJwtException) {
            setResponse(response, ExceptionCode.E_401_000, e.message)
        } catch (e: ExpiredJwtException) {
            setResponse(response, ExceptionCode.E_401_001, e.message)
        } catch (e: SignatureException) {
            setResponse(response, ExceptionCode.E_401_000, e.message)
        }
    }

    private fun setResponse(
        response: HttpServletResponse,
        code: ExceptionCode,
        detail: String? = null,
    ) {
        response.contentType = "application/json;charset=UTF-8"
        response.status = code.httpStatus.value()

        val errorBody = translator.translate(code)

        val responseJson = JSONObject()
        responseJson["code"] = errorBody.code
        responseJson["message"] = errorBody.message
        responseJson["title"] = errorBody.title
        responseJson["detail"] = detail

        response.writer.print(responseJson)
    }
}
