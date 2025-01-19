package kr.co.shoppingcart.cart.domain.user.services

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.user.UserRepository
import kr.co.shoppingcart.cart.domain.user.enums.LoginProvider
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserUpdateService(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun deleteUser(
        id: Long,
        provider: LoginProvider,
    ) = userRepository.deleteByIdAndLoginType(id, provider)
        ?: throw CustomException.responseBody(code = ExceptionCode.E_404_004)
}
