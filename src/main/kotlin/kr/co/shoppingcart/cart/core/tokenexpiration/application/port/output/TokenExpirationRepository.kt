package kr.co.shoppingcart.cart.core.tokenexpiration.application.port.output

import kr.co.shoppingcart.cart.core.tokenexpiration.domain.TokenExpiration
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface TokenExpirationRepository<T, ID> : Repository<T, ID> {
    fun getByName(name: String): TokenExpiration?
}
