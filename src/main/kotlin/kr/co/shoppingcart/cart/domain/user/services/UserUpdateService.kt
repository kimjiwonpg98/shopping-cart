package kr.co.shoppingcart.cart.domain.user.services

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.user.UserRepository
import kr.co.shoppingcart.cart.domain.user.enums.LoginProvider
import kr.co.shoppingcart.cart.domain.user.vo.User
import kr.co.shoppingcart.cart.external.kakao.KakaoClient
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserUpdateService(
    private val userRepository: UserRepository,
    private val kakaoClient: KakaoClient,
) {
    @Transactional
    fun deleteUser(
        id: Long,
        provider: LoginProvider,
    ) = userRepository.deleteByIdAndLoginType(id, provider)
        ?: throw CustomException.responseBody(code = ExceptionCode.E_404_004)

    fun unlinkProvider(user: User) {
        if (user.provider.provider == LoginProvider.KAKAO) {
            kakaoClient.unlinkUser(user.authIdentifier.authIdentifier)
        }
    }
}
