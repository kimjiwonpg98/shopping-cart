package kr.co.shoppingcart.cart.database.mysql.basket.mapper

import kr.co.shoppingcart.cart.database.mysql.basket.entity.BasketEntity
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.category.vo.Category
import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.utils.DateUtil.convertZonedDateTimeToSeoulTime

object BasketEntityMapper {
    fun toDomain(basketEntity: BasketEntity): Basket =
        Basket.toDomain(
            id = basketEntity.id,
            name = basketEntity.content,
            count = basketEntity.count,
            checked = basketEntity.checked,
            createdTime = basketEntity.createdAt?.let { convertZonedDateTimeToSeoulTime(it) },
            updatedTime = basketEntity.updatedAt?.let { convertZonedDateTimeToSeoulTime(it) },
            categoryName = basketEntity.categoryName,
            categoryId = basketEntity.category.id,
            templateId = basketEntity.template.id,
            category = Category.toDomain(id = basketEntity.category.id, name = basketEntity.category.name),
            template =
                Template.toDomain(
                    id = basketEntity.template.id!!,
                    name = basketEntity.template.name,
                    userId = basketEntity.template.userId,
                    isPublic = basketEntity.template.isPublic,
                    isPinned = basketEntity.template.isPinned,
                    thumbnailIndex = basketEntity.template.thumbnailIdx,
                    createdAt =
                        basketEntity.template.createdAt?.let { convertZonedDateTimeToSeoulTime(it) },
                    updatedAt =
                        basketEntity.template.createdAt?.let { convertZonedDateTimeToSeoulTime(it) },
                ),
        )
}
