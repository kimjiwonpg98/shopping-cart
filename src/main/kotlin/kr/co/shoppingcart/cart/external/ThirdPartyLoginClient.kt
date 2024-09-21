package kr.co.shoppingcart.cart.external

interface ThirdPartyLoginClient {
    fun getTokenByGrantCode(grantCode: String): String?
}
