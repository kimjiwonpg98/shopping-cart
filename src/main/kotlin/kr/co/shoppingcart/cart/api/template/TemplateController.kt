package kr.co.shoppingcart.cart.api.template

import io.swagger.v3.oas.annotations.Operation
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
import kr.co.shoppingcart.cart.api.template.dto.response.GetCountResponseDto
import kr.co.shoppingcart.cart.api.template.dto.response.GetTemplateByIdResponseBodyDto
import kr.co.shoppingcart.cart.api.template.dto.response.GetTemplateResponseBodyDto
import kr.co.shoppingcart.cart.api.template.dto.response.PinnedTemplateResponse
import kr.co.shoppingcart.cart.api.template.dto.response.UnpinnedTemplateResponse
import kr.co.shoppingcart.cart.api.template.dto.response.UpdateTemplateResponseBodyDto
import kr.co.shoppingcart.cart.api.template.dto.response.UpdateTemplateToSharedResponseBodyDto
import kr.co.shoppingcart.cart.auth.JwtPayload
import kr.co.shoppingcart.cart.auth.annotation.CurrentUser
import kr.co.shoppingcart.cart.common.error.annotations.OpenApiSpecApiException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.template.TemplateUseCase
import kr.co.shoppingcart.cart.domain.template.command.CopyOwnTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.CopyTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.CopyTemplateInCompleteCommand
import kr.co.shoppingcart.cart.domain.template.command.CreateTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.DeleteByTemplateIdCommand
import kr.co.shoppingcart.cart.domain.template.command.GetTemplateByIdAndUserIdCommand
import kr.co.shoppingcart.cart.domain.template.command.GetWithCompletePercentAndPreviewCommand
import kr.co.shoppingcart.cart.domain.template.command.PinnedTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.UnpinnedTemplateCommand
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
) {
    @OpenApiSpecApiException(
        [
            ExceptionCode.E_400_000,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "장바구니 리스트 추가", description = "이름 기본값: 장바구니")
    @PostMapping("/v1/template")
    fun save(
        @Valid @RequestBody(required = false) @Nullable body: CreateTemplateRequestBodyDto?,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<CreateTemplateResponseBodyDto> {
        val name =
            body?.name ?: "장바구니 ${templateUseCase.getDefaultNameByUserId(currentUser.identificationValue.toLong())}"

        val template =
            CreateTemplateCommand(
                name = name,
                userId = currentUser.identificationValue.toLong(),
            )

        val result = templateUseCase.createByApi(template)
        return ResponseEntity.status(201).body(
            CreateTemplateResponseBodyDto(
                result = result.let(TemplateResponseMapper::toResponse),
            ),
        )
    }

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_401_000,
            ExceptionCode.E_403_000,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "장바구니 리스트 조회", description = "장바구니 id로 조회")
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

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_401_000,
            ExceptionCode.E_403_000,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "장바구니 리스트 내용 변경", description = "이름과 섬네일 이미지 변경")
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

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_401_000,
            ExceptionCode.E_403_000,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "장바구니 공유 여부 변경", description = "장바구니 공유 여부 변경(소유자만 가능)")
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

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_401_000,
            ExceptionCode.E_403_000,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "장바구니 체크 안된 물품만 카피(소유자만 가능)", description = "소유자만 가능")
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

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_401_000,
            ExceptionCode.E_403_000,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "장바구니 전체 카피(소유자만 가능)", description = "소유자만 가능")
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

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_401_000,
            ExceptionCode.E_403_000,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "공유된 장바구니 카피 (public인 경우 가능)", description = "public인 경우 가능")
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

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_401_000,
            ExceptionCode.E_403_000,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "본인의 장바구니 리스트 조회", description = "퍼센트까지 모두 조회")
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

        val (pinnedTemplates, unPinnedTemplates) =
            templates.partition { it.isPinned }

        return ResponseEntity.ok().body(
            GetTemplateResponseBodyDto(
                pinned = pinnedTemplates.map(TemplateResponseMapper::toResponseWithPercentAndPreview),
                result = unPinnedTemplates.map(TemplateResponseMapper::toResponseWithPercentAndPreview),
            ),
        )
    }

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_401_000,
            ExceptionCode.E_403_000,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "장바구니 리스트 삭제", description = "id로 삭제")
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

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_401_000,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "본인의 장바구니 개수 조회", description = "토큰 필요")
    @GetMapping("/v1/templates/count")
    fun getCount(
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<GetCountResponseDto> {
        val count = templateUseCase.getCountByUserId(currentUser.identificationValue.toLong())

        return ResponseEntity.ok().body(
            GetCountResponseDto(
                count = count,
            ),
        )
    }

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_401_000,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
            ExceptionCode.E_403_002,
        ],
    )
    @Operation(summary = "장바구니 고정", description = "토큰 필요")
    @PutMapping("/v1/templates/pinned/{id}")
    fun pinnedTemplate(
        @CurrentUser currentUser: JwtPayload,
        @PathVariable id: String,
    ): ResponseEntity<PinnedTemplateResponse> {
        templateUseCase.pinnedTemplate(
            PinnedTemplateCommand(
                userId = currentUser.identificationValue.toLong(),
                templateId = id.toLong(),
            ),
        )

        return ResponseEntity.ok().body(
            PinnedTemplateResponse(),
        )
    }

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_401_000,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "장바구니 고정 취소", description = "토큰 필요")
    @PutMapping("/v1/templates/unpinned/{id}")
    fun unpinnedTemplate(
        @CurrentUser currentUser: JwtPayload,
        @PathVariable id: String,
    ): ResponseEntity<UnpinnedTemplateResponse> {
        templateUseCase.unpinnedTemplate(
            UnpinnedTemplateCommand(
                userId = currentUser.identificationValue.toLong(),
                templateId = id.toLong(),
            ),
        )

        return ResponseEntity.ok().body(
            UnpinnedTemplateResponse(),
        )
    }
}
