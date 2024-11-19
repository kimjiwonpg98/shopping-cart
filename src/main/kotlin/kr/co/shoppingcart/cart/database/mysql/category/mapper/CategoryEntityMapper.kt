package kr.co.shoppingcart.cart.database.mysql.category.mapper

import kr.co.shoppingcart.cart.database.mysql.category.entity.CategoryEntity
import kr.co.shoppingcart.cart.domain.category.vo.Category

object CategoryEntityMapper {
    fun toDomain(entity: CategoryEntity): Category =
        Category.toDomain(
            id = entity.id,
            name = entity.name,
        )

    fun toEntity(category: Category): CategoryEntity =
        CategoryEntity(
            id = category.id.id,
            name = category.name.name,
        )
}
