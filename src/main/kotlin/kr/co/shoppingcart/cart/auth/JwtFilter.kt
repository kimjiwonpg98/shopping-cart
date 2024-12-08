package kr.co.shoppingcart.cart.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.co.shoppingcart.cart.auth.enums.TokenInformationEnum
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val jwtProvider: JwtProvider,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (authorizationHeader == null || !authorizationHeader.startsWith(PREFIX)) {
            filterChain.doFilter(request, response)
            return
        }

        val token =
            authorizationHeader
                .split(" ".toRegex())
                .dropLastWhile {
                    it.isEmpty()
                }.toTypedArray()[1]
        val jwtPayload: JwtPayload = jwtProvider.verifyToken(token)
        request.setAttribute(TokenInformationEnum.USER.name, jwtPayload)
        setAuthentication(jwtPayload)
        filterChain.doFilter(request, response)
    }

    companion object {
        private const val PREFIX = "Bearer "
    }

    private fun setAuthentication(jwtPayload: JwtPayload) {
        val username = jwtPayload.identificationValue
        val authorities = emptyList<GrantedAuthority>()

        val user = User(username, "", authorities)
        val auth = UsernamePasswordAuthenticationToken(user, null, authorities)
        SecurityContextHolder.getContext().authentication = auth
    }
}
