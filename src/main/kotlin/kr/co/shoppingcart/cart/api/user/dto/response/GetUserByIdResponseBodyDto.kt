package kr.co.shoppingcart.cart.api.user.dto.response

import kr.co.shoppingcart.cart.domain.user.vo.User

data class GetUserByIdResponseBodyDto(
    val id: Long,
    val provider: String,
    val email: String,
) {
    companion object {
        fun of(user: User): GetUserByIdResponseBodyDto =
            GetUserByIdResponseBodyDto(
                id = user.userId.id,
                provider = user.provider.provider.name,
                email = user.userEmail.email ?: "",
            )
    }
}
