package kr.co.shoppingcart.cart.api.basket

import kr.co.shoppingcart.cart.api.basket.dto.request.CheckedBasketReqBodyDto
import kr.co.shoppingcart.cart.api.basket.dto.response.GetByTemplateIdResDto
import kr.co.shoppingcart.cart.domain.basket.BasketUseCase
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketsByTemplateIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketFlagCommand
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class WebSocketBasketController(
    private val messagingTemplate: SimpMessagingTemplate,
    private val basketUseCase: BasketUseCase,
) {
    @MessageMapping("/template/{templateId}")
    fun getByTemplateId(
        @DestinationVariable templateId: String,
        @Header("userId") userId: String,
    ) {
        val result =
            basketUseCase.getOwnByTemplateId(
                GetBasketsByTemplateIdCommand(
                    templateId.toLong(),
                    userId.toLong(),
                ),
            )
        messagingTemplate.convertAndSend(
            "/template/$templateId",
            GetByTemplateIdResDto(
                result = result.map(BasketResponseMapper::toResponse),
            ),
        )
    }

    @MessageMapping("/{templateId}/check")
    fun updateForCheck(
        @DestinationVariable templateId: String,
        @Header("userId") userId: String,
        @Payload dto: CheckedBasketReqBodyDto,
    ) {
        val basket =
            UpdateBasketFlagCommand(
                userId = userId.toLong(),
                basketId = dto.basketId,
                checked = dto.checked,
            )

        val updatedBasket = basketUseCase.updateIsAddedByFlagAndId(basket)

        messagingTemplate.convertAndSendToUser(userId, "/get/template/$templateId", updatedBasket)
    }
}
