package kr.co.shoppingcart.cart.database.mysql.basket

import kr.co.shoppingcart.cart.database.mysql.basket.entity.BasketJdbcEntity
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement

@Repository
class BasketJdbcRepository(
    private val jdbcTemplate: JdbcTemplate,
) {
    fun bulkInsert(basketEntities: List<BasketJdbcEntity>) {
        val sql =
            "INSERT INTO basket(count, content, checked, is_pinned, template_id, category_id) VALUES (?, ?, ?, ?, ?, ?)"

        val batch =
            object : BatchPreparedStatementSetter {
                override fun setValues(
                    ps: PreparedStatement,
                    i: Int,
                ) {
                    val basket = basketEntities[i]
                    ps.setLong(1, basket.count)
                    ps.setString(2, basket.content)
                    ps.setBoolean(3, basket.checked)
                    ps.setBoolean(4, basket.isPinned)
                    ps.setLong(5, basket.templateId)
                    ps.setLong(6, basket.categoryId)
                }

                override fun getBatchSize(): Int = basketEntities.size
            }

        jdbcTemplate.batchUpdate(sql, batch)
    }
}
