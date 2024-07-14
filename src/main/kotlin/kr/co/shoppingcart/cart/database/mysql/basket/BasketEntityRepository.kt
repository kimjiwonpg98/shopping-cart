package kr.co.shoppingcart.cart.database.mysql.basket

import kr.co.shoppingcart.cart.database.mysql.basket.entity.BasketEntity
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param

@NoRepositoryBean
interface BasketEntityRepository<T, ID>: Repository<T, ID> {
    fun save(basketEntity: BasketEntity): BasketEntity
    fun getByTemplateIdOrderByUpdatedAtDesc(templateId: Long): List<BasketEntity>
    fun getByTemplateIdAndCategoryId(templateId: Long, categoryId: Long): List<BasketEntity>
    fun getById(id: Long): BasketEntity?
    fun updateCheckedById(id: Long, checked: Boolean)
}