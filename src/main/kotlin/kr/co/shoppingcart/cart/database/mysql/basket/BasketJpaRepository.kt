package kr.co.shoppingcart.cart.database.mysql.basket

import kr.co.shoppingcart.cart.database.mysql.basket.entity.BasketEntity
import org.springframework.stereotype.Repository

@Repository
interface BasketJpaRepository: BasketEntityRepository<BasketEntity, Long> {
    override fun save(basketEntity: BasketEntity): BasketEntity

    override fun getByTemplateIdOrderByUpdatedAtDesc(templateId: Long): List<BasketEntity>

    override fun getByTemplateIdAndCategoryId(templateId: Long, categoryId: Long): List<BasketEntity>

    override fun getById(id: Long): BasketEntity?

    override fun updateCheckedById(id: Long, checked: Boolean) {
        val basket = getById(id)
        basket!!.checked = checked
    }
}