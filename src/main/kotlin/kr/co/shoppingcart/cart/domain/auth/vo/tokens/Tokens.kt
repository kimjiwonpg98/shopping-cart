package kr.co.shoppingcart.cart.domain.auth.vo.tokens

data class Tokens(
    val accessToken: AccessToken,
    val refreshToken: RefreshToken
) {
    companion object {
        fun toDomain(accessToken: String, refreshToken: String): Tokens =
            Tokens(AccessToken(accessToken), RefreshToken(refreshToken))
    }
}
