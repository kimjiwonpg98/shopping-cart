package kr.co.shoppingcart.cart.external.common

import kr.co.shoppingcart.cart.domain.user.CreateUserUseCase
import kr.co.shoppingcart.cart.domain.user.command.LoginCommand
import kr.co.shoppingcart.cart.domain.user.enums.LoginType
import kr.co.shoppingcart.cart.domain.user.enums.UserProperties
import mu.KotlinLogging
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class Oauth2Service(
    private val createUserUseCase: CreateUserUseCase,
) : DefaultOAuth2UserService() {
    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        userRequest.accessToken
        // OAuth 제공자 식별
        val registrationId = userRequest.clientRegistration.registrationId

        // 해당 provider의 enum 값을 찾음
        val userProperties =
            UserProperties.entries.firstOrNull {
                it.registrationId.equals(registrationId, ignoreCase = true)
            } ?: throw OAuth2AuthenticationException("Unsupported OAuth Provider: $registrationId")

        // 사용자 프로필 추출
        val userProfile = userProperties.extract(registrationId, oAuth2User.attributes)

        // User 생성 요청
        val loginCommand =
            LoginCommand(
                email = "test10@test.com",
                loginType = LoginType.KAKAO.name,
                gender = "M",
                birth = "1998",
            )

        val user = createUserUseCase.createUser(loginCommand)
        val attributes = HashMap(oAuth2User.attributes)
        attributes.putIfAbsent("userId", user.userId.id)
        attributes.putIfAbsent("loginType", user.loginType.loginType.name)

        // 권한 부여
        val authorities = AuthorityUtils.createAuthorityList("ROLE_USER")

        // 사용자 정보 반환
        return DefaultOAuth2User(
            authorities,
            attributes,
            userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName,
        )
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
