package kr.co.shoppingcart.cart.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.co.shoppingcart.cart.auth.enums.TokenInformationEnum
import kr.co.shoppingcart.cart.common.error.CustomException
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val jwtProvider: JwtProvider
): OncePerRequestFilter() {
    @Throws(CustomException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (authorizationHeader == null || !authorizationHeader.startsWith(PREFIX)) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authorizationHeader.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
        val jwtPayload: JwtPayload = jwtProvider.verifyToken(token)
        request.setAttribute(TokenInformationEnum.USER.name, jwtPayload)
        filterChain.doFilter(request, response)
    }

    companion object {
        private const val PREFIX = "Bearer "
    }
}