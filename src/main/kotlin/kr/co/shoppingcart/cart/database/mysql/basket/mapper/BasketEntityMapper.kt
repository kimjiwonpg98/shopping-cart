package kr.co.shoppingcart.cart.database.mysql.basket.mapper

import kr.co.shoppingcart.cart.database.mysql.basket.entity.BasketEntity
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.category.vo.Category
import kr.co.shoppingcart.cart.domain.template.vo.Template
import java.time.ZoneId

object BasketEntityMapper {
    fun toDomain(basketEntity: BasketEntity): Basket =
        Basket.toDomain(
            name = basketEntity.content,
            count = basketEntity.count,
            checked = basketEntity.checked,
            createTime = basketEntity.createdAt?.toLocalDateTime(),
            updateTime = basketEntity.updatedAt?.toLocalDateTime(),
            categoryId = basketEntity.template.id,
            templateId = basketEntity.category.id,
            category = Category.toDomain(id = basketEntity.category.id!!, name = basketEntity.category.name),
            template =
                Template.toDomain(
                    id = basketEntity.template.id!!,
                    name = basketEntity.template.name,
                    userId = basketEntity.template.userId,
                    isPublic = basketEntity.template.isPublic,
                    createdAt =
                        basketEntity.template.createdAt?.toLocalDateTime()?.atZone(
                            ZoneId.of("Asia/Seoul"),
                        ),
                    updatedAt =
                        basketEntity.template.createdAt?.toLocalDateTime()?.atZone(
                            ZoneId.of("Asia/Seoul"),
                        ),
                ),
        )
}
