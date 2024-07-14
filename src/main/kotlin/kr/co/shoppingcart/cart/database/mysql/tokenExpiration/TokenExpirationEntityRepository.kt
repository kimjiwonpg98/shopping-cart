package kr.co.shoppingcart.cart.database.mysql.tokenExpiration

import kr.co.shoppingcart.cart.database.mysql.tokenExpiration.entity.TokenExpirationEntity
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface TokenExpirationEntityRepository<T, ID> : Repository<T, ID> {
    fun getByName(name: String): TokenExpirationEntity?
}
