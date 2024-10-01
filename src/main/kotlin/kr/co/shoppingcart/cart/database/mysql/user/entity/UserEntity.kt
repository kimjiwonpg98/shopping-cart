package kr.co.shoppingcart.cart.database.mysql.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import kr.co.shoppingcart.cart.database.mysql.common.entity.CommonEntity
import kr.co.shoppingcart.cart.database.mysql.user.entity.UserEntity.Companion.USER_ENTITY_NAME
import java.util.Date

@Entity(name = USER_ENTITY_NAME)
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT", name = "id")
    val id: Long? = null,
    @Column(nullable = true, length = 200)
    val email: String?,
    @Column(nullable = false, length = 30, name = "login_type")
    val loginType: String,
    @Column(nullable = true, length = 2)
    val gender: String?,
    @Column(nullable = true)
    val birth: Date?,
) : CommonEntity() {
    companion object {
        const val USER_ENTITY_NAME = "user"
    }
}
