package kr.co.shoppingcart.cart.database.mysql.template.jdbc.mapper

import kr.co.shoppingcart.cart.database.mysql.template.jdbc.dto.TemplateWithCheckedCountDto
import kr.co.shoppingcart.cart.utils.DateUtil.convertLocalDateTimeToSeoulTime
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

object GetWithCheckedCountMapper {
    val rowMapper: RowMapper<TemplateWithCheckedCountDto>
        get() =
            RowMapper<TemplateWithCheckedCountDto> { rs: ResultSet, _: Int ->
                TemplateWithCheckedCountDto(
                    checkedCount = rs.getLong("checkedCount"),
                    unCheckedCount = rs.getLong("unCheckedCount"),
                    id = rs.getLong("id"),
                    name = rs.getString("name"),
                    userId = rs.getLong("userId"),
                    isPublic = rs.getBoolean("isPublic"),
                    createdAt = convertLocalDateTimeToSeoulTime(rs.getTimestamp("createdAt").toLocalDateTime()),
                    updatedAt = convertLocalDateTimeToSeoulTime(rs.getTimestamp("updatedAt").toLocalDateTime()),
                )
            }
}
