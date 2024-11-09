package kr.co.shoppingcart.cart.database.mysql.template.jdbc

import kr.co.shoppingcart.cart.database.mysql.template.jdbc.dto.TemplateWithCheckedCountDto
import kr.co.shoppingcart.cart.database.mysql.template.jdbc.mapper.GetWithCheckedCountMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository

@Repository
class TemplateJdbcRepository(
    private val jdbcTemplate: JdbcTemplate,
) {
    fun getWithCheckedCount(userId: Long): List<TemplateWithCheckedCountDto> {
        val sql =
            """
            SELECT
                SUM(IF(b.checked = true, 1, 0)) AS checkedCount,
                SUM(IF(b.checked = false, 1, 0)) AS unCheckedCount,
                t.id,
                t.name,
                t.user_id AS userId,
                t.is_public AS isPublic,
                t.thumbnail_idx AS thumbnailIndex,
                t.created_at AS createdAt,
                t.updated_at AS updatedAt
            FROM template AS t
            LEFT JOIN basket AS b ON t.id = b.template_id
            WHERE t.user_id = ?
            GROUP BY t.id
            ORDER BY t.id DESC
            """.trimIndent()

        return jdbcTemplate.query(sql, GetWithCheckedCountMapper.rowMapper, userId)
    }

    fun getOneWithCheckedCount(templateId: Long): TemplateWithCheckedCountDto? {
        val sql =
            """
            SELECT
                SUM(IF(b.checked = true, 1, 0)) AS checkedCount,
                SUM(IF(b.checked = false, 1, 0)) AS unCheckedCount,
                t.id,
                t.name,
                t.user_id AS userId,
                t.is_public AS isPublic,
                t.thumbnail_idx AS thumbnailIndex,
                t.created_at AS createdAt,
                t.updated_at AS updatedAt
            FROM template AS t
            LEFT JOIN basket AS b ON t.id = b.template_id
            WHERE t.id = ?
            """.trimIndent()

        return jdbcTemplate.queryForObject(sql, GetWithCheckedCountMapper.rowMapper, templateId)
    }
}
