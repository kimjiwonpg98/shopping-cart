package kr.co.shoppingcart.cart.database.mysql.user

import kr.co.shoppingcart.cart.database.mysql.user.entity.UserEntity
import kr.co.shoppingcart.cart.database.mysql.user.mapper.UserEntityMapper
import kr.co.shoppingcart.cart.domain.user.UserRepository
import kr.co.shoppingcart.cart.domain.user.enums.LoginProvider
import kr.co.shoppingcart.cart.domain.user.vo.User
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class UserRepositoryAdapter(
    private val userEntityRepository: UserEntityRepository<UserEntity, Long>,
) : UserRepository {
    override fun create(
        email: String?,
        provider: LoginProvider,
        authIdentifier: String,
        gender: String?,
        ageRange: String?,
    ): User =
        userEntityRepository
            .save(
                UserEntity(
                    email = email,
                    provider = provider.name,
                    authIdentifier = authIdentifier,
                    gender = gender,
                    ageRange = ageRange,
                ),
            ).let(UserEntityMapper::toDomain)

    override fun getByUniqueKeyAndLoginType(
        authIdentifier: String,
        provider: LoginProvider,
    ): User? =
        userEntityRepository
            .getByAuthIdentifierAndProvider(
                authIdentifier,
                provider.name,
            )?.let(UserEntityMapper::toDomain)

    override fun deleteByUniqueKeyAndLoginType(
        authIdentifier: String,
        provider: LoginProvider,
    ): User? {
        val user =
            userEntityRepository
                .getByAuthIdentifierAndProvider(
                    authIdentifier,
                    provider.name,
                ) ?: return null

        user.deletedAt = ZonedDateTime.now()

        return user.let(UserEntityMapper::toDomain)
    }
}
