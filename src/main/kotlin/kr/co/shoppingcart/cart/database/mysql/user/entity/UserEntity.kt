package kr.co.shoppingcart.cart.database.mysql.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import kr.co.shoppingcart.cart.database.mysql.common.entity.CommonEntity
import kr.co.shoppingcart.cart.database.mysql.user.entity.UserEntity.Companion.USER_ENTITY_NAME
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.ZonedDateTime

@Entity(name = USER_ENTITY_NAME)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE auth_identifier = ?")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT", name = "id")
    val id: Long? = null,
    @Column(nullable = false)
    val authIdentifier: String,
    @Column(nullable = false, length = 30)
    val provider: String,
    @Column(nullable = true, length = 200)
    val email: String?,
    @Column(nullable = true, length = 2)
    val gender: String?,
    @Column(nullable = true)
    val ageRange: String?,
    @Column(name = "deleted_at")
    var deletedAt: ZonedDateTime? = null,
) : CommonEntity() {
    companion object {
        const val USER_ENTITY_NAME = "user"
    }
}
