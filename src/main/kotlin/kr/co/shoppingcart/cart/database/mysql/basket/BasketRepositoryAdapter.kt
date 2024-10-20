package kr.co.shoppingcart.cart.database.mysql.basket

import kr.co.shoppingcart.cart.database.mysql.basket.entity.BasketEntity
import kr.co.shoppingcart.cart.database.mysql.basket.entity.BasketJdbcEntity
import kr.co.shoppingcart.cart.database.mysql.basket.mapper.BasketEntityMapper
import kr.co.shoppingcart.cart.database.mysql.category.entity.CategoryEntity
import kr.co.shoppingcart.cart.database.mysql.template.entity.TemplateEntity
import kr.co.shoppingcart.cart.domain.basket.BasketRepository
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.category.vo.Category
import kr.co.shoppingcart.cart.domain.template.vo.Template
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class BasketRepositoryAdapter(
    private val basketEntityRepository: BasketEntityRepository<BasketEntity, Long>,
    private val basketJdbcRepository: BasketJdbcRepository,
) : BasketRepository {
    override fun save(
        basket: Basket,
        template: Template,
        category: Category,
    ): Basket {
        val categoryEntity =
            CategoryEntity(
                id = category.id.id,
                name = category.name.name,
            )
        val templateEntity =
            TemplateEntity(
                id = template.id.id,
                name = template.name.name,
                userId = template.userId.userId,
            )

        return basketEntityRepository
            .save(
                BasketEntity(
                    content = basket.name.name,
                    count = basket.count.count,
                    checked = basket.checked.checked,
                    isPinned = false,
                    template = templateEntity,
                    category = categoryEntity,
                ),
            ).let(BasketEntityMapper::toDomain)
    }

    override fun getByTemplateId(templateId: Long): List<Basket> =
        basketEntityRepository.getByTemplateIdOrderByUpdatedAtDesc(templateId).map(BasketEntityMapper::toDomain)

    override fun getByTemplateIdWithPageNation(
        templateId: Long,
        page: Long,
        size: Long,
    ): List<Basket> {
        val sort: Sort =
            Sort.by(
                Sort.Order(Sort.Direction.ASC, "checked"),
                Sort.Order(Sort.Direction.DESC, "updatedAt"),
            )
        val pageable: Pageable = PageRequest.of(page.toInt(), size.toInt(), sort)
        return basketEntityRepository
            .findByTemplateId(
                templateId,
                pageable,
            ).map(BasketEntityMapper::toDomain)
    }

    override fun getById(basketId: Long): Basket? =
        basketEntityRepository.findById(basketId)?.let(BasketEntityMapper::toDomain)

    override fun getByTemplateIdAndSizeOrderByUpdatedDesc(
        templateId: Long,
        size: Int,
    ): List<Basket> =
        basketEntityRepository
            .getByTemplateIdOrderByUpdatedAtDesc(
                templateId,
                PageRequest.of(0, size),
            ).map(BasketEntityMapper::toDomain)

    override fun updateCheckedById(
        basketId: Long,
        checked: Boolean,
    ): Basket {
        val basket = basketEntityRepository.findById(basketId)
        basket!!.checked = checked
        return basket.let(BasketEntityMapper::toDomain)
    }

    override fun bulkSave(basket: List<Basket>) {
        basketJdbcRepository.bulkInsert(
            basket.map {
                BasketJdbcEntity(
                    content = it.name.name,
                    count = it.count.count,
                    checked = it.checked.checked,
                    isPinned = false,
                    categoryId = it.categoryId!!.categoryId,
                    templateId = it.templateId!!.templateId,
                )
            },
        )
    }
}
