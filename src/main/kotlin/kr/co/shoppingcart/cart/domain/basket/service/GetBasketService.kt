package kr.co.shoppingcart.cart.domain.basket.service

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.BasketRepository
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import org.springframework.stereotype.Component

@Component
class GetBasketService(
    private val basketRepository: BasketRepository,
) {
    fun getByTemplateIdAndCategoryIdByUpdatedDesc(
        templateId: Long,
        categoryId: Long,
    ): List<Basket> = basketRepository.getByTemplateIdAndCategoryIdByUpdatedDesc(templateId, categoryId)

    fun getByTemplateIdAndCategoryNameByUpdatedDesc(
        templateId: Long,
        categoryName: String,
    ): List<Basket> = basketRepository.getByTemplateIdAndCategoryNameByUpdatedDesc(templateId, categoryName)

    fun getByTemplateIdAndSizeOrderByUpdatedDesc(
        templateId: Long,
        size: Int,
    ): List<Basket> = basketRepository.getByTemplateIdAndSizeOrderByUpdatedDesc(templateId, size)

    fun getByTemplateId(templateId: Long): List<Basket> = basketRepository.getByTemplateId(templateId)

    fun getByIdOrFail(id: Long): Basket =
        basketRepository.getById(id) ?: throw CustomException.responseBody(
            ExceptionCode.E_404_002,
        )

    fun getByIds(ids: List<Long>): List<Basket> = basketRepository.getByIds(ids)
}
