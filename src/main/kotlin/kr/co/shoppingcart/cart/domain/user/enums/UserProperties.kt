package kr.co.shoppingcart.cart.domain.user.enums
//
// import kr.co.shoppingcart.cart.domain.user.vo.User
//
// enum class UserProperties(
//    val provider: String,
//    val extractProfile: (Map<String, Any>) -> User,
// ) {
//    KAKAO("kakao", { attribute ->
//        val account = attribute["kakao_account"] as Map<String, *>
//        val profile = account["profile"] as Map<String, *>
//        User.toDomain(
//            email = "",
//            loginType = LoginType.KAKAO.name,
//            userId = 1,
//            gender = account.getValue("gender") as String,
//            birth = account.getValue("d") as String,
//        )
//    }),
// }
