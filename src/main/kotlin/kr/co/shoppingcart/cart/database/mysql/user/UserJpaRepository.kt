package kr.co.shoppingcart.cart.database.mysql.user

import kr.co.shoppingcart.cart.database.mysql.user.entity.UserEntity
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository : UserEntityRepository<UserEntity, Long> {
    override fun save(userEntity: UserEntity): UserEntity

    override fun getByAuthIdentifierAndProvider(
        authIdentifier: String,
        provider: String,
    ): UserEntity?
}
