package kr.co.shoppingcart.cart.database.mysql.tokenExpiration

import kr.co.shoppingcart.cart.database.mysql.tokenExpiration.entity.TokenExpirationEntity
import kr.co.shoppingcart.cart.domain.auth.TokenExpirationRepository
import kr.co.shoppingcart.cart.domain.auth.mapper.TokenJpaMapper
import kr.co.shoppingcart.cart.domain.auth.vo.expiration.TokenExpiration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TokenExpirationRepositoryAdapter(
    private val tokenExpirationEntityRepository: TokenExpirationEntityRepository<TokenExpirationEntity, Long>,
) : TokenExpirationRepository {
    @Transactional(readOnly = true)
    override fun getByName(name: String): TokenExpiration? =
        tokenExpirationEntityRepository.getByName(name)?.let(TokenJpaMapper::toDomain)
}
