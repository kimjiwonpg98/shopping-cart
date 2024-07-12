package kr.co.shoppingcart.cart.database.mysql.basket

import kr.co.shoppingcart.cart.database.mysql.basket.entity.BasketEntity
import kr.co.shoppingcart.cart.database.mysql.category.entity.CategoryEntity
import kr.co.shoppingcart.cart.database.mysql.template.entity.TemplateEntity
import kr.co.shoppingcart.cart.domain.basket.BasketRepository
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.basket.vo.BasketChecked
import kr.co.shoppingcart.cart.domain.category.vo.Category
import kr.co.shoppingcart.cart.domain.template.vo.Template
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId

@Component
class BasketRepositoryAdapter(
    private val basketEntityRepository: BasketEntityRepository<BasketEntity, Long>
): BasketRepository {
    override fun save(basket: Basket, template: Template, category: Category) {
        val categoryEntity = CategoryEntity(
            id = category.id.id,
            name = category.name.name
        )
        val templateEntity = TemplateEntity(
            id = template.id.id,
            name = template.name.name,
            userId = template.userId.userId,
        )

        basketEntityRepository.save(
            BasketEntity(
                content = basket.name.name,
                count = basket.count.count,
                checked = basket.checked.checked,
                isPinned = false,
                template = templateEntity,
                category = categoryEntity
            )
        )
    }

    override fun getByTemplateId(templateId: Long): List<Basket> =
        basketEntityRepository.getByTemplateIdOrderByUpdatedAtDesc(templateId).map {
            Basket.toDomain(
                name = it.content,
                count = it.count,
                checked = it.checked,
                createTime = it.createdAt?.toLocalDateTime(),
                updateTime = it.updatedAt?.toLocalDateTime(),
                category = Category.toDomain(id = it.category.id!!, name = it.category.name),
                template = Template.toDomain(
                    id = it.template.id!!,
                    name = it.template.name,
                    userId = it.template.userId,
                    isPublic = it.template.isPublic,
                    createdAt = it.template.createdAt?.toLocalDateTime()?.atZone(ZoneId.of("Asia/Seoul")),
                    updatedAt = it.template.createdAt?.toLocalDateTime()?.atZone(ZoneId.of("Asia/Seoul"))
                )
            )
        }

    override fun getById(basketId: Long): Basket? =
        basketEntityRepository.getById(basketId)?.let { Basket.toDomain(
            name = it.content,
            count = it.count,
            checked = it.checked,
            createTime = it.createdAt?.toLocalDateTime(),
            updateTime = it.updatedAt?.toLocalDateTime(),
            category = Category.toDomain(id = it.category.id!!, name = it.category.name),
            template = Template.toDomain(
                id = it.template.id!!,
                name = it.template.name,
                userId = it.template.userId,
                isPublic = it.template.isPublic,
                createdAt = it.template.createdAt?.toLocalDateTime()?.atZone(ZoneId.of("Asia/Seoul")),
                updatedAt = it.template.createdAt?.toLocalDateTime()?.atZone(ZoneId.of("Asia/Seoul"))
            )
        )}

    @Transactional
    override fun updateCheckedById(basketId: Long, checked: Boolean) {
        basketEntityRepository.updateCheckedById(basketId, checked)
    }

}