package kr.co.shoppingcart.cart.api.user

import kr.co.shoppingcart.cart.api.user.dto.request.LoginRequestBodyDto
import kr.co.shoppingcart.cart.api.user.dto.response.LoginResponseBodyDto
import kr.co.shoppingcart.cart.auth.JwtPayload
import kr.co.shoppingcart.cart.auth.annotation.CurrentUser
import kr.co.shoppingcart.cart.common.error.annotations.OpenApiSpecApiException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.auth.CreateTokensUseCase
import kr.co.shoppingcart.cart.domain.user.UserUseCase
import kr.co.shoppingcart.cart.domain.user.command.DeleteUserCommand
import kr.co.shoppingcart.cart.domain.user.command.GetByAuthIdentifierAndProviderCommand
import kr.co.shoppingcart.cart.domain.user.command.LoginCommand
import kr.co.shoppingcart.cart.domain.user.enums.LoginProvider
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userUseCase: UserUseCase,
    private val createTokensUseCase: CreateTokensUseCase,
) {
    @OpenApiSpecApiException([ExceptionCode.E_401_000])
    @PostMapping("/v1/login")
    fun login(
        @RequestBody user: LoginRequestBodyDto,
    ): ResponseEntity<LoginResponseBodyDto> {
        val loginCommand =
            GetByAuthIdentifierAndProviderCommand(
                user.authIdentifier,
                user.provider,
            )

        val userInfo = userUseCase.getByAuthIdentifierAndProviderOrFail(loginCommand)
        val tokens = createTokensUseCase.createTokensByUser(userInfo)

        val responseBody =
            LoginResponseBodyDto(
                tokens.accessToken.token,
                tokens.refreshToken.token,
            )

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody)
    }

    @PostMapping("/v1/user")
    fun signup(
        @RequestBody user: LoginRequestBodyDto,
    ): ResponseEntity<Unit> {
        val loginCommand =
            LoginCommand(
                user.email,
                user.provider,
                user.authIdentifier,
                user.gender,
                user.ageRange,
            )
        userUseCase.createIfAbsent(loginCommand)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/v1/user")
    fun delete(
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<Unit> {
        userUseCase.deleteUser(
            DeleteUserCommand(
                currentUser.identificationValue,
                LoginProvider.valueOf(currentUser.provider),
            ),
        )
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
