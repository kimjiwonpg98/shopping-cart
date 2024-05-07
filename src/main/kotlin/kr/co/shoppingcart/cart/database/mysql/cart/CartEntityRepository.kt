package kr.co.shoppingcart.cart.database.mysql.cart

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface CartEntityRepository<T, ID>: Repository<T, ID> {
    fun saveCart(name: String, userId: Long)
}