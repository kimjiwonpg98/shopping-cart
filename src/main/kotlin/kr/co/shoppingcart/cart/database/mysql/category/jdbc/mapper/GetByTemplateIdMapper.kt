package kr.co.shoppingcart.cart.database.mysql.category.jdbc.mapper

import kr.co.shoppingcart.cart.database.mysql.category.jdbc.dto.GetCategoryByTemplateIdDto
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

object GetByTemplateIdMapper {
    val rowMapper: RowMapper<GetCategoryByTemplateIdDto>
        get() =
            RowMapper<GetCategoryByTemplateIdDto> { rs: ResultSet, _: Int ->
                GetCategoryByTemplateIdDto(
                    categoryId = rs.getLong("categoryId"),
                    categoryName = rs.getString("categoryName"),
                )
            }
}
