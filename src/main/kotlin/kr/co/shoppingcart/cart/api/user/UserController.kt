package kr.co.shoppingcart.cart.api.user

import kr.co.shoppingcart.cart.api.user.dto.request.LoginRequestBodyDto
import kr.co.shoppingcart.cart.api.user.dto.response.LoginResponseBodyDto
import kr.co.shoppingcart.cart.common.error.annotations.OpenApiSpecApiException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.auth.vo.CreateTokensUseCase
import kr.co.shoppingcart.cart.domain.user.CreateUserUseCase
import kr.co.shoppingcart.cart.domain.user.GetUserUseCase
import kr.co.shoppingcart.cart.domain.user.command.LoginCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val createUserUseCase: CreateUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val createTokensUseCase: CreateTokensUseCase,
) {
    @OpenApiSpecApiException([ExceptionCode.E_401_000])
    @PostMapping("/login")
    fun login(
        @RequestBody user: LoginRequestBodyDto,
    ): ResponseEntity<LoginResponseBodyDto> {
        val loginCommand =
            LoginCommand(
                user.email,
                user.loginType,
            )

        val userInfo = getUserUseCase.getByEmailAndLoginType(loginCommand)
        val tokens = createTokensUseCase.createTokensByUser(loginCommand, userInfo)

        val responseBody =
            LoginResponseBodyDto(
                tokens.accessToken.token,
                tokens.refreshToken.token,
            )

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody)
    }

    @PostMapping("/user")
    fun signup(
        @RequestBody user: LoginRequestBodyDto,
    ): ResponseEntity<Unit> {
        val loginCommand =
            LoginCommand(
                user.email,
                user.loginType,
            )
        createUserUseCase.createUser(loginCommand)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
