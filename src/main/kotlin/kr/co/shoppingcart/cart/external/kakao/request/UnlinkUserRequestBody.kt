package kr.co.shoppingcart.cart.external.kakao.request

data class UnlinkUserRequestBody(
    val authIdentifier: Long,
) {
    fun toFormDataRequestBody(): String = "target_id=$authIdentifier&target_id_type=user_id"
}
