package kr.co.shoppingcart.cart.database.mysql.template.jdbc

import kr.co.shoppingcart.cart.database.mysql.template.jdbc.dto.TemplateWithCheckedCountDto
import kr.co.shoppingcart.cart.database.mysql.template.jdbc.mapper.GetWithCheckedCountMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class TemplateJdbcRepository(
    private val jdbcTemplate: JdbcTemplate,
) {
    fun getWithCheckedCount(
        userId: Long,
        page: Long,
        size: Long,
    ): List<TemplateWithCheckedCountDto> {
        val firstLimit = (page - 1) * size
        val sql =
            """
            SELECT
                SUM(IF(b.checked = true, 1, 0)) AS checkedCount,
                SUM(IF(b.checked = false, 1, 0)) AS unCheckedCount,
                t.id,
                t.name,
                t.user_id AS userId,
                t.is_public AS isPublic,
                t.created_at AS createdAt,
                t.updated_at AS updatedAt
            FROM template AS t
            LEFT JOIN basket AS b ON t.id = b.template_id
            WHERE t.user_id = ?
            GROUP BY t.id
            ORDER BY t.id DESC
            LIMIT $firstLimit, $size
            """.trimIndent()

        return jdbcTemplate.query(sql, GetWithCheckedCountMapper.rowMapper, userId)
    }
}
