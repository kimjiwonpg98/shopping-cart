package kr.co.shoppingcart.cart.external.common

import kr.co.shoppingcart.cart.domain.user.CreateUserUseCase
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

        // Role generate
        val authorities = AuthorityUtils.createAuthorityList("ROLE_USER")

        // nameAttributeKey
        val userNameAttributeName =
            userRequest.clientRegistration
                .providerDetails
                .userInfoEndpoint
                .userNameAttributeName
        logger.info("User name attribute name is $userNameAttributeName")

//        val loginCommand =
//            LoginCommand(
//                email = "test@test.com",
//                loginType = "KAKAO",
//                gender = Gender.MALE.value,
//                birth = "1998",
//            )
//
//        createUserUseCase.createUser(loginCommand)

        return DefaultOAuth2User(authorities, oAuth2User.attributes, userNameAttributeName)
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
