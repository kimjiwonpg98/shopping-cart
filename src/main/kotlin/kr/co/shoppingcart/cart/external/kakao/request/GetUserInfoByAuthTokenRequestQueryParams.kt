package kr.co.shoppingcart.cart.external.kakao.request

import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

data class GetUserInfoByAuthTokenRequestQueryParams(
    val propertiesKey: List<String>,
) {
    fun toQueryParams(): MultiValueMap<String, String> = LinkedMultiValueMap<String, String>()
}
