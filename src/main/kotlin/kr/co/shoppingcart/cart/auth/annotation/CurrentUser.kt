package kr.co.shoppingcart.cart.auth.annotation

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
//@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member")
annotation class CurrentUser {}