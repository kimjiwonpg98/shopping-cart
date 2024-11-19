package kr.co.shoppingcart.cart.api.template

import jakarta.annotation.Nullable
import jakarta.validation.Valid
import kr.co.shoppingcart.cart.api.template.dto.request.CreateTemplateRequestBodyDto
import kr.co.shoppingcart.cart.api.template.dto.request.GetWIthPercentRequestParamsDto
import kr.co.shoppingcart.cart.api.template.dto.request.UpdateTemplateRequestDto
import kr.co.shoppingcart.cart.api.template.dto.request.UpdateTemplateSharedRequestBodyDto
import kr.co.shoppingcart.cart.api.template.dto.response.CopyPublicTemplateAllResponseBodyDto
import kr.co.shoppingcart.cart.api.template.dto.response.CopyTemplateAllResponseBodyDto
import kr.co.shoppingcart.cart.api.template.dto.response.CopyTemplateIncompleteResponseBodyDto
import kr.co.shoppingcart.cart.api.template.dto.response.CreateTemplateResponseBodyDto
import kr.co.shoppingcart.cart.api.template.dto.response.GetTemplateByIdResponseBodyDto
import kr.co.shoppingcart.cart.api.template.dto.response.GetTemplateResponseBodyDto
import kr.co.shoppingcart.cart.api.template.dto.response.UpdateTemplateResponseBodyDto
import kr.co.shoppingcart.cart.api.template.dto.response.UpdateTemplateToSharedResponseBodyDto
import kr.co.shoppingcart.cart.auth.JwtPayload
import kr.co.shoppingcart.cart.auth.annotation.CurrentUser
import kr.co.shoppingcart.cart.common.error.annotations.OpenApiSpecApiException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.BasketUseCase
import kr.co.shoppingcart.cart.domain.template.TemplateUseCase
import kr.co.shoppingcart.cart.domain.template.command.CopyOwnTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.CopyTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.CopyTemplateInCompleteCommand
import kr.co.shoppingcart.cart.domain.template.command.CreateTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.DeleteByTemplateIdCommand
import kr.co.shoppingcart.cart.domain.template.command.GetTemplateByIdAndUserIdCommand
import kr.co.shoppingcart.cart.domain.template.command.GetWithCompletePercentAndPreviewCommand
import kr.co.shoppingcart.cart.domain.template.command.UpdateTemplateByIdCommand
import kr.co.shoppingcart.cart.domain.template.command.UpdateTemplateSharedByIdCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TemplateController(
    private val templateUseCase: TemplateUseCase,
    private val basketUseCase: BasketUseCase,
) {
    @PostMapping("/v1/template")
    fun save(
        @Valid @RequestBody(required = false) @Nullable body: CreateTemplateRequestBodyDto?,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<CreateTemplateResponseBodyDto> {
        val template =
            CreateTemplateCommand(
                name = body?.name ?: "장바구니",
                userId = currentUser.identificationValue.toLong(),
            )
        val result = templateUseCase.createByApi(template)
        return ResponseEntity.status(201).body(
            CreateTemplateResponseBodyDto(
                result = result.let(TemplateResponseMapper::toResponse),
            ),
        )
    }

    @OpenApiSpecApiException([ExceptionCode.E_401_000, ExceptionCode.E_403_000])
    @GetMapping("/v1/template/{id}")
    fun getById(
        @PathVariable id: Long,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<GetTemplateByIdResponseBodyDto> {
        val command =
            GetTemplateByIdAndUserIdCommand(
                id,
                currentUser.identificationValue.toLong(),
            )
        val template = templateUseCase.getByIdAndUserIdToRead(command)

        return ResponseEntity.status(200).body(
            GetTemplateByIdResponseBodyDto(
                result = template.let(TemplateResponseMapper::toResponseWithPercent),
            ),
        )
    }

    @OpenApiSpecApiException([ExceptionCode.E_401_000, ExceptionCode.E_403_000])
    @PutMapping("/v1/template/{id}")
    fun updateTemplate(
        @PathVariable id: String,
        @Valid @RequestBody @Nullable body: UpdateTemplateRequestDto?,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<UpdateTemplateResponseBodyDto> {
        val result =
            templateUseCase.updateById(
                UpdateTemplateByIdCommand(
                    templateId = id.toLong(),
                    userId = currentUser.identificationValue.toLong(),
                    name = body?.name,
                    thumbnailIndex = body?.thumbnailIndex,
                ),
            )
        return ResponseEntity.status(200).body(
            UpdateTemplateResponseBodyDto(
                result = result.let(TemplateResponseMapper::toResponse),
            ),
        )
    }

    @OpenApiSpecApiException([ExceptionCode.E_401_000, ExceptionCode.E_403_000])
    @PutMapping("/v1/template/{id}/share")
    fun updateSharedById(
        @PathVariable id: String,
        @RequestBody params: UpdateTemplateSharedRequestBodyDto,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<UpdateTemplateToSharedResponseBodyDto> {
        val result =
            templateUseCase.updateSharedById(
                UpdateTemplateSharedByIdCommand(
                    id = id.toLong(),
                    userId = currentUser.identificationValue.toLong(),
                    isShared = params.isShared,
                ),
            )
        return ResponseEntity.status(200).body(
            UpdateTemplateToSharedResponseBodyDto(
                result = result.let(TemplateResponseMapper::toResponse),
            ),
        )
    }

    @OpenApiSpecApiException([ExceptionCode.E_401_000, ExceptionCode.E_403_000])
    @PostMapping("/v1/template/{id}/copy/incomplete")
    fun copyTemplateIncomplete(
        @PathVariable id: String,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<CopyTemplateIncompleteResponseBodyDto> {
        val result =
            templateUseCase.copyOwnTemplateInComplete(
                CopyTemplateInCompleteCommand(
                    id = id.toLong(),
                    userId = currentUser.identificationValue.toLong(),
                ),
            )
        return ResponseEntity.status(201).body(
            CopyTemplateIncompleteResponseBodyDto(
                result = result.let(TemplateResponseMapper::toResponse),
            ),
        )
    }

    @OpenApiSpecApiException([ExceptionCode.E_401_000, ExceptionCode.E_403_000])
    @PostMapping("/v1/template/{id}/copy")
    fun copyTemplateAll(
        @PathVariable id: String,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<CopyTemplateAllResponseBodyDto> {
        val result =
            templateUseCase.copyOwnTemplate(
                CopyOwnTemplateCommand(
                    id = id.toLong(),
                    userId = currentUser.identificationValue.toLong(),
                ),
            )

        return ResponseEntity.status(201).body(
            CopyTemplateAllResponseBodyDto(
                result = result.let(TemplateResponseMapper::toResponse),
            ),
        )
    }

    @OpenApiSpecApiException([ExceptionCode.E_401_000, ExceptionCode.E_403_000])
    @PostMapping("/v1/template/public/{id}/copy")
    fun copyPublicTemplateAll(
        @PathVariable id: String,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<CopyPublicTemplateAllResponseBodyDto> {
        val result =
            templateUseCase.copyTemplate(
                CopyTemplateCommand(
                    id = id.toLong(),
                    userId = currentUser.identificationValue.toLong(),
                ),
            )

        return ResponseEntity.status(201).body(
            CopyPublicTemplateAllResponseBodyDto(
                result = result.let(TemplateResponseMapper::toResponse),
            ),
        )
    }

    @OpenApiSpecApiException([ExceptionCode.E_401_000, ExceptionCode.E_403_000])
    @GetMapping("/v1/templates")
    fun getAll(
        @CurrentUser currentUser: JwtPayload,
        @ModelAttribute params: GetWIthPercentRequestParamsDto?,
    ): ResponseEntity<GetTemplateResponseBodyDto> {
        val command =
            GetWithCompletePercentAndPreviewCommand(
                currentUser.identificationValue.toLong(),
            )
        val templates = templateUseCase.getWithCompletePercentAndPreview(command)

        return ResponseEntity.ok().body(
            GetTemplateResponseBodyDto(
                result =
                    templates.map { template ->
                        if (template.checkedCount.count != 0L && template.unCheckedCount.count != 0L) {
                            val basketNames =
                                basketUseCase
                                    .getByTemplateIdAndSizeOrderByUpdatedDesc(
                                        templateId = template.id.id,
                                        size =
                                            params?.previewCount?.toIntOrNull() ?: 3,
                                    ).map { it.name.name }
                            TemplateResponseMapper.toResponseWithPercentAndPreview(template, basketNames)
                        }
                        val basketNames = emptyList<String>()

                        TemplateResponseMapper.toResponseWithPercentAndPreview(template, basketNames)
                    },
            ),
        )
    }

    @OpenApiSpecApiException([ExceptionCode.E_401_000, ExceptionCode.E_403_000])
    @DeleteMapping("/v1/template/{id}")
    fun deleteById(
        @PathVariable id: String,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<Unit> {
        val command =
            DeleteByTemplateIdCommand(
                templateId = id.toLong(),
                userId = currentUser.identificationValue.toLong(),
            )
        templateUseCase.deleteByIdAndUserId(command)

        return ResponseEntity.accepted().build()
    }
}
