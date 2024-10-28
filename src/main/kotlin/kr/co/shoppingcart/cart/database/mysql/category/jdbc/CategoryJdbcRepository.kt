package kr.co.shoppingcart.cart.database.mysql.category.jdbc

import kr.co.shoppingcart.cart.database.mysql.category.jdbc.dto.GetCategoryByTemplateIdDto
import kr.co.shoppingcart.cart.database.mysql.category.jdbc.mapper.GetByTemplateIdMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CategoryJdbcRepository(
    private val jdbcTemplate: JdbcTemplate,
) {
    fun getByTemplateId(templateId: Long): List<GetCategoryByTemplateIdDto> {
        val sql =
            """
            SELECT
                distinct(c.name) AS categoryName,
                c.id AS categoryId
                FROM basket AS b
                LEFT JOIN category c
                ON b.category_id = c.id
                WHERE b.template_id= ?
            """.trimIndent()

        return jdbcTemplate.query(sql, GetByTemplateIdMapper.rowMapper, templateId)
    }
}
