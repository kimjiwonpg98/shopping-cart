package kr.co.shoppingcart.cart.core.tokenexpiration.application.port.output

import kr.co.shoppingcart.cart.core.tokenexpiration.domain.TokenExpiration
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TokenExpirationJpaRepository : TokenExpirationRepository<TokenExpiration, Long> {
    @Query("SELECT t FROM token_expiration t WHERE t.name = :name")
    override fun getByName(
        @Param("name") name: String,
    ): TokenExpiration?
}
