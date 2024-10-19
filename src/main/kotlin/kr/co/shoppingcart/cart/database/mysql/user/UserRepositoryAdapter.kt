package kr.co.shoppingcart.cart.database.mysql.user

import kr.co.shoppingcart.cart.database.mysql.user.entity.UserEntity
import kr.co.shoppingcart.cart.database.mysql.user.mapper.UserEntityMapper
import kr.co.shoppingcart.cart.domain.user.UserRepository
import kr.co.shoppingcart.cart.domain.user.enums.LoginType
import kr.co.shoppingcart.cart.domain.user.vo.User
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter(
    private val userEntityRepository: UserEntityRepository<UserEntity, Long>,
) : UserRepository {
    override fun create(
        email: String,
        loginType: LoginType,
        gender: String?,
        birth: String?,
    ): User =
        userEntityRepository
            .save(
                UserEntity(
                    email = email,
                    loginType = loginType.name,
                    gender = gender,
                    birth = birth,
                ),
            ).let(UserEntityMapper::toDomain)

    override fun getByEmailAndLoginType(
        email: String,
        loginType: LoginType,
    ): User? =
        userEntityRepository
            .getByEmailAndLoginType(
                email,
                loginType.name,
            )?.let(UserEntityMapper::toDomain)
}
