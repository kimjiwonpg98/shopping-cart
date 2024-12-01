package kr.co.shoppingcart.cart.auth

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.co.shoppingcart.cart.domain.auth.service.TokenService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CustomSuccessHandler(
    @Value("\${jwt.redirect-local-url}")
    private val localRedirectUrl: String,
    @Value("\${jwt.redirect-url}")
    private val redirectUrl: String,
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

        val randomKey = UUID.randomUUID().toString().replace("-".toRegex(), "")

        tokenService.settingCacheTokenByOauth2(randomKey, accessToken, refreshToken)

        val referer = request.getHeader("Referer")

        if (referer == "http://localhost:3000/") {
            redirectStrategy.sendRedirect(
                request,
                response,
                "$localRedirectUrl/bridge?q=$randomKey",
            )
        } else {
            redirectStrategy.sendRedirect(
                request,
                response,
                "$redirectUrl/bridge?q=$randomKey",
            )
        }
    }
}
