package kr.co.shoppingcart.cart.mock

import kr.co.shoppingcart.cart.domain.basket.BasketRepository
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.category.vo.Category
import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.mock.vo.MockBasket

class MockBasketRepository : BasketRepository {
    override fun save(
        basket: Basket,
        template: Template,
        category: Category,
    ) {
    }

    override fun getByTemplateId(templateId: Long): List<Basket> = MockBasket.getBaskets()

    override fun getByTemplateIdWithPageNation(
        templateId: Long,
        page: Long,
        size: Long,
    ): List<Basket> = MockBasket.getBaskets()

    override fun getById(basketId: Long): Basket? = MockBasket.getBasket(basketId)

    override fun getByTemplateIdAndSizeOrderByUpdatedDesc(
        templateId: Long,
        size: Int,
    ): List<Basket> = MockBasket.getBaskets()

    override fun updateCheckedById(
        basketId: Long,
        checked: Boolean,
    ) {
    }

    override fun bulkSave(basket: List<Basket>) {
    }
}
