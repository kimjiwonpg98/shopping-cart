package kr.co.shoppingcart.cart.domain.basket

import kr.co.shoppingcart.cart.domain.basket.vo.Basket

interface BasketRepository {
    fun save(basket: Basket): Basket

    fun getByTemplateId(templateId: Long): List<Basket>

    fun getByTemplateIdWithPageNation(
        templateId: Long,
        page: Long,
        size: Long,
    ): List<Basket>

    fun getById(basketId: Long): Basket?

    fun getByIds(basketIds: List<Long>): List<Basket>

    fun getByTemplateIdAndSizeOrderByUpdatedDesc(
        templateId: Long,
        size: Int,
    ): List<Basket>

    fun getByTemplateIdAndCategoryIdByUpdatedDesc(
        templateId: Long,
        categoryId: Long,
    ): List<Basket>

    fun updateCheckedById(
        basketId: Long,
        checked: Boolean,
    ): Basket

    fun updateContent(
        basketId: Long,
        content: String?,
        count: Long?,
    ): Basket

    fun deleteById(basketId: Long)

    fun deleteByIds(basketIds: List<Long>)

    fun bulkSave(basket: List<Basket>)

    fun updateCheckedByIds(
        basketIds: List<Long>,
        checked: Boolean,
    )
}
