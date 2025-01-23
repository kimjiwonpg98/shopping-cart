package kr.co.shoppingcart.cart.database.mysql.basket

import kr.co.shoppingcart.cart.database.mysql.basket.entity.BasketEntity
import kr.co.shoppingcart.cart.database.mysql.basket.entity.BasketJdbcEntity
import kr.co.shoppingcart.cart.database.mysql.basket.mapper.BasketEntityMapper
import kr.co.shoppingcart.cart.database.mysql.category.mapper.CategoryEntityMapper
import kr.co.shoppingcart.cart.database.mysql.template.mapper.TemplateEntityMapper
import kr.co.shoppingcart.cart.domain.basket.BasketRepository
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class BasketRepositoryAdapter(
    private val basketEntityRepository: BasketEntityRepository<BasketEntity, Long>,
    private val basketJdbcRepository: BasketJdbcRepository,
) : BasketRepository {
    override fun save(basket: Basket): Basket {
        val categoryEntity = CategoryEntityMapper.toEntity(basket.category)

        val templateEntity =
            TemplateEntityMapper.toEntity(basket.template!!)

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
        basketEntityRepository
            .getByTemplateIdOrderByCheckedAscUpdatedAtDesc(
                templateId,
            ).map(BasketEntityMapper::toDomain)

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

    override fun getByIds(basketIds: List<Long>): List<Basket> =
        basketEntityRepository.findByIdIn(basketIds).map(BasketEntityMapper::toDomain)

    override fun getByTemplateIdAndSizeOrderByUpdatedDesc(
        templateId: Long,
        size: Int,
    ): List<Basket> =
        basketEntityRepository
            .getByTemplateIdOrderByUpdatedAtDesc(
                templateId,
                PageRequest.of(0, size),
            ).map(BasketEntityMapper::toDomain)

    override fun getByTemplateIdAndCategoryIdByUpdatedDesc(
        templateId: Long,
        categoryId: Long,
    ): List<Basket> =
        basketEntityRepository
            .getByTemplateIdAndCategoryIdOrderByUpdatedAtDescCheckedAsc(
                templateId,
                categoryId,
            ).map(BasketEntityMapper::toDomain)

    override fun updateCheckedById(
        basketId: Long,
        checked: Boolean,
    ): Basket {
        val basket = basketEntityRepository.findById(basketId)
        basket!!.checked = checked
        return basket.let(BasketEntityMapper::toDomain)
    }

    override fun updateContent(
        basketId: Long,
        content: String?,
        count: Long?,
    ): Basket {
        val basket = basketEntityRepository.findById(basketId)
        basket!!.content = content ?: basket.content
        basket.count = count ?: basket.count
        return basket.let(BasketEntityMapper::toDomain)
    }

    override fun deleteById(basketId: Long) = basketEntityRepository.deleteById(basketId)

    override fun deleteByIds(basketIds: List<Long>) = basketEntityRepository.deleteByIdIn(basketIds)

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

    override fun updateCheckedByIds(
        basketIds: List<Long>,
        checked: Boolean,
    ) = basketEntityRepository.updateCheckedByIdIn(basketIds, checked)
}
