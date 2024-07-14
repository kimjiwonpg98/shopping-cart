package kr.co.shoppingcart.cart.database.mysql.user

import kr.co.shoppingcart.cart.database.mysql.user.entity.UserEntity
import kr.co.shoppingcart.cart.domain.user.UserRepository
import kr.co.shoppingcart.cart.domain.user.enums.LoginType
import kr.co.shoppingcart.cart.domain.user.mapper.UserJpaMapper
import kr.co.shoppingcart.cart.domain.user.vo.User
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserRepositoryAdapter(
    private val userEntityRepository: UserEntityRepository<UserEntity, Long>,
) : UserRepository {
    @Transactional(readOnly = false)
    override fun create(
        email: String,
        loginType: LoginType,
    ): User =
        userEntityRepository.save(
            UserEntity(
                email = email,
                loginType = loginType.name,
            ),
        ).let(UserJpaMapper::toDomain)

    @Transactional(readOnly = true)
    override fun getByEmailAndLoginType(
        email: String,
        loginType: LoginType,
    ): User? =
        userEntityRepository.getByEmailAndLoginType(
            email,
            loginType.name,
        )?.let(UserJpaMapper::toDomain)
}
