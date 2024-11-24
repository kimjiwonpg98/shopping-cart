package kr.co.shoppingcart.cart.auth

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.co.shoppingcart.cart.domain.auth.service.TokenService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Service

@Service
class CustomSuccessHandler(
    private val tokenService: TokenService,
) : AuthenticationSuccessHandler {
    private val redirectStrategy = DefaultRedirectStrategy()

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        val oauth2User: OAuth2User = authentication.principal as OAuth2User
        val attributes: MutableMap<String, Any> = oauth2User.attributes

        val accessToken =
            tokenService.createAccessToken(
                userId = attributes["userId"] as Long,
                provider = attributes["provider"] as String,
            )

        val refreshToken =
            tokenService.createRefreshToken(
                userId = attributes["userId"] as Long,
                provider = attributes["provider"] as String,
            )

        response.addHeader(
            HttpHeaders.SET_COOKIE,
            ResponseCookie
                .from("accessToken", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .build()
                .toString(),
        )

        response.addHeader(
            HttpHeaders.SET_COOKIE,
            ResponseCookie
                .from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build()
                .toString(),
        )

        redirectStrategy.sendRedirect(request, response, "https://kka-dam.vercel.app/home")
    }
}
