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

    override fun getByIds(basketIds: List<Long>): List<Basket> {
        TODO("Not yet implemented")
    }

    override fun getByTemplateIdAndSizeOrderByUpdatedDesc(
        templateId: Long,
        size: Int,
    ): List<Basket> = MockBasket.getBasketsAllChecked()

    override fun getByTemplateIdAndCategoryIdByUpdatedDesc(
        templateId: Long,
        categoryId: Long,
    ): List<Basket> = MockBasket.getBasketsAllChecked()

    override fun getByTemplateIdAndCategoryNameByUpdatedDesc(
        templateId: Long,
        categoryName: String,
    ): List<Basket> {
        TODO("Not yet implemented")
    }

    override fun getCategoriesByTemplateId(templateId: Long): List<String> {
        TODO("Not yet implemented")
    }

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

    override fun deleteByIds(basketIds: List<Long>) {
        TODO("Not yet implemented")
    }

    override fun bulkSave(basket: List<Basket>) {
    }

    override fun updateCheckedByIds(
        basketIds: List<Long>,
        checked: Boolean,
    ) {
        TODO("Not yet implemented")
    }

    override fun updateCategoryName(
        basketIds: List<Long>,
        categoryName: String,
    ) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val BASKET_ID_RETURN_NULL = 100L
    }
}
