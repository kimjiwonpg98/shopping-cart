package kr.co.shoppingcart.cart.domain.auth.mapper

import kr.co.shoppingcart.cart.database.mysql.tokenExpiration.entity.TokenExpirationEntity
import kr.co.shoppingcart.cart.domain.auth.vo.expiration.RefreshTokenTTL
import kr.co.shoppingcart.cart.domain.auth.vo.expiration.TokenExpiration
import kr.co.shoppingcart.cart.domain.auth.vo.expiration.TokenName
import kr.co.shoppingcart.cart.domain.auth.vo.expiration.TokenTTL

object TokenJpaMapper {
    fun toDomain(tokenExpirationEntity: TokenExpirationEntity): TokenExpiration =
        TokenExpiration(
            tokenName = TokenName(tokenExpirationEntity.name),
            tokenTTL = TokenTTL(tokenExpirationEntity.ttl),
            refreshTokenTTL = RefreshTokenTTL(tokenExpirationEntity.refreshTTL),
        )
}