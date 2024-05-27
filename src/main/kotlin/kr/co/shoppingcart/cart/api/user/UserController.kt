package kr.co.shoppingcart.cart.api.user

import kr.co.shoppingcart.cart.api.user.dto.LoginRequestBodyDto
import kr.co.shoppingcart.cart.api.user.dto.LoginResponseBodyDto
import kr.co.shoppingcart.cart.domain.auth.vo.CreateTokensUseCase
import kr.co.shoppingcart.cart.domain.user.CreateUserUseCase
import kr.co.shoppingcart.cart.domain.user.command.LoginCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController (
    private val createUserUseCase: CreateUserUseCase,
    private val createTokensUseCase: CreateTokensUseCase
) {
    @PostMapping("/login")
    fun login(@RequestBody user: LoginRequestBodyDto): ResponseEntity<LoginResponseBodyDto> {
        val loginCommand = LoginCommand(
            user.email,
            user.loginType
        )

        val userInfo = createUserUseCase.createUser(loginCommand)
        val tokens = createTokensUseCase.createTokensByUser(loginCommand, userInfo)

        val responseBody =  LoginResponseBodyDto(
            tokens.accessToken.token,
            tokens.refreshToken.token
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody)
    }
}