package kr.co.shoppingcart.cart.database.mysql.cart

import kr.co.shoppingcart.cart.database.mysql.cart.entity.CartEntity
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface CartEntityRepository<T, ID>: Repository<T, ID> {
    fun save(cartEntity: CartEntity): CartEntity
}