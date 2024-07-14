package kr.co.shoppingcart.cart.external

interface ThirdPartyLoginClient {
    fun getGrantCode(): String?
}
