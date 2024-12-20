package kr.co.shoppingcart.cart.mock

import kr.co.shoppingcart.cart.domain.basket.BasketRepository
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.mock.vo.MockBasket

class MockBasketRepository : BasketRepository {
    override fun save(basket: Basket): Basket = MockBasket.getBasket(1, true)

    override fun getByTemplateId(templateId: Long): List<Basket> = MockBasket.getBasketsAllNonChecked()

    override fun getByTemplateIdWithPageNation(
        templateId: Long,
        page: Long,
        size: Long,
    ): List<Basket> = MockBasket.getBasketsAllChecked()

    override fun getById(basketId: Long): Basket? =
        MockBasket.getOptionalBasket(
            basketId,
            basketId == BASKET_ID_RETURN_NULL,
        )

    override fun getByTemplateIdAndSizeOrderByUpdatedDesc(
        templateId: Long,
        size: Int,
    ): List<Basket> = MockBasket.getBasketsAllChecked()

    override fun getByTemplateIdAndCategoryIdByUpdatedDesc(
        templateId: Long,
        categoryId: Long,
    ): List<Basket> = MockBasket.getBasketsAllChecked()

    override fun updateCheckedById(
        basketId: Long,
        checked: Boolean,
    ): Basket = MockBasket.getBasket(1, true)

    override fun updateContent(
        basketId: Long,
        content: String?,
        count: Long?,
    ): Basket = MockBasket.getBasket(1, true)

    override fun deleteById(basketId: Long) {
    }

    override fun bulkSave(basket: List<Basket>) {
    }

    companion object {
        private const val BASKET_ID_RETURN_NULL = 100L
    }
}
