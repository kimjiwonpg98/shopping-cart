package kr.co.shoppingcart.cart.database.mysql.tokenExpiration

import kr.co.shoppingcart.cart.database.mysql.tokenExpiration.entity.TokenExpirationEntity
import kr.co.shoppingcart.cart.database.mysql.tokenExpiration.mapper.TokenJpaMapper
import kr.co.shoppingcart.cart.domain.auth.TokenExpirationRepository
import kr.co.shoppingcart.cart.domain.auth.vo.expiration.TokenExpiration
import org.springframework.stereotype.Component

@Component
class TokenExpirationRepositoryAdapter(
    private val tokenExpirationEntityRepository: TokenExpirationEntityRepository<TokenExpirationEntity, Long>,
) : TokenExpirationRepository {
    override fun getByName(name: String): TokenExpiration? =
        tokenExpirationEntityRepository.getByName(name)?.let(TokenJpaMapper::toDomain)
}
