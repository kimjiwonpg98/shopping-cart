package kr.co.shoppingcart.cart.domain.vo

import kr.co.shoppingcart.cart.domain.user.enums.LoginType
import kr.co.shoppingcart.cart.domain.user.vo.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Test

class UserTest {
    @Test
    fun `값들을 넣으면 User 객체로 응답한다`() {
        // given 테스트 변수로 생략

        // when
        val user: User = User.toDomain(email, loginType, userId, gender, birth)

        // then
        assertEquals(user.userId.id, userId)
        assertEquals(user.userEmail.email, email)
        assertEquals(user.loginType.loginType, LoginType.valueOf(loginType))
        assertNotNull(user.userEmail.email)
    }

    @Test
    fun `요청값은 string이지만 응답은 enum타입으로 나온다`() {
        // given 테스트 변수로 생략

        // when
        val user: User = User.toDomain(email, loginType, userId, gender, birth)

        // then
        assertEquals(user.loginType.loginType, LoginType.valueOf(loginType))
        assertNotSame(user.loginType.loginType, loginType)
    }

    companion object {
        private val email = "test@test.com"
        private val loginType = "KAKAO"
        private val userId = 1L
        private val gender = "F"
        private val birth = "1990"
    }
}
