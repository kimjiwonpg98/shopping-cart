package kr.co.shoppingcart.cart.database.mysql.user

import kr.co.shoppingcart.cart.database.mysql.user.entity.UserEntity
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface UserEntityRepository<T, ID> : Repository<T, ID> {
    fun save(userEntity: UserEntity): UserEntity

    fun getByAuthIdentifierAndProvider(
        authIdentifier: String,
        provider: String,
    ): UserEntity?

    fun findById(id: Long): UserEntity?

    fun findByIdAndProvider(
        id: Long,
        provider: String,
    ): UserEntity?
}
