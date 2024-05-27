package kr.co.shoppingcart.cart.database.mysql.tokenExpiration

import kr.co.shoppingcart.cart.database.mysql.tokenExpiration.entity.TokenExpirationEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TokenExpirationJpaRepository: TokenExpirationEntityRepository<TokenExpirationEntity, Long> {
    @Query("SELECT t FROM tokenExp t WHERE t.name = :name")
    override fun getByName(@Param("name") name: String): TokenExpirationEntity?
}