package kr.co.shoppingcart.cart.domain.template.services

import kr.co.shoppingcart.cart.domain.basket.BasketRepository
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import kr.co.shoppingcart.cart.domain.template.vo.Template
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class CreateTemplateService(
    private val templateRepository: TemplateRepository,
    private val basketRepository: BasketRepository,
) {
    @Transactional(propagation = Propagation.MANDATORY)
    fun create(
        name: String,
        thumbnailIndex: Int,
        userId: Long,
    ): Template =
        templateRepository.create(
            name = name,
            thumbnailIndex = thumbnailIndex,
            userId = userId,
        )

    @Transactional(propagation = Propagation.MANDATORY)
    fun createNewBasketsByTemplateId(
        baskets: List<Basket>,
        templateId: Long,
    ) {
        baskets.map { it.templateId!!.templateId = templateId }
        basketRepository.bulkSave(baskets)
    }
}
