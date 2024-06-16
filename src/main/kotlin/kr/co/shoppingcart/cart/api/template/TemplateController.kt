package kr.co.shoppingcart.cart.api.template

import jakarta.validation.Valid
import kr.co.shoppingcart.cart.api.template.dto.CreateTemplateRequestBodyDto
import kr.co.shoppingcart.cart.auth.JwtPayload
import kr.co.shoppingcart.cart.auth.annotation.CurrentUser
import kr.co.shoppingcart.cart.domain.template.TemplateUseCase
import kr.co.shoppingcart.cart.domain.template.command.CreateTemplateCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TemplateController (
    private val templateUseCase: TemplateUseCase
) {
    @PostMapping("/template")
    fun save(
        @Valid @RequestBody body: CreateTemplateRequestBodyDto,
        @CurrentUser currentUser: JwtPayload
    ): ResponseEntity<Unit> {
        val cart = CreateTemplateCommand(name = body.name, userId = currentUser.identificationValue.toLong())
        templateUseCase.save(cart)
        return ResponseEntity.status(201).build()
    }
}