package kr.co.shoppingcart.cart.api.template

import jakarta.validation.Valid
import kr.co.shoppingcart.cart.api.template.dto.`in`.CreateTemplateRequestBodyDto
import kr.co.shoppingcart.cart.api.template.dto.out.GetTemplateByIdResponseBodyDto
import kr.co.shoppingcart.cart.auth.JwtPayload
import kr.co.shoppingcart.cart.auth.annotation.CurrentUser
import kr.co.shoppingcart.cart.common.error.annotations.OpenApiSpecApiException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.template.TemplateUseCase
import kr.co.shoppingcart.cart.domain.template.command.CreateTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.GetTemplateByIdAndUserIdCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @OpenApiSpecApiException([ExceptionCode.E_401_000])
    @GetMapping("/template/{id}")
    fun getById(
        @PathVariable id: String,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<GetTemplateByIdResponseBodyDto> {
        val command = GetTemplateByIdAndUserIdCommand(
            id,
            currentUser.identificationValue.toLong()
        )
        val template = templateUseCase.getByIdAndUserId(command)

        return ResponseEntity.status(200).body(GetTemplateByIdResponseBodyDto(
            result = template.let(TemplateResponseMapper::toDomain)
        ))
    }

    @PatchMapping("/template/{id}/visibility")
    fun updateVisibilityById(
        @PathVariable id: String,
        @CurrentUser currentUser: JwtPayload
    ): ResponseEntity<Unit> {
        return ResponseEntity.status(200).build();
    }
}