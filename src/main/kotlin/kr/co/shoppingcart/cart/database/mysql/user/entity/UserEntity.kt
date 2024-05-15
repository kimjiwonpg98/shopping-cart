package kr.co.shoppingcart.cart.database.mysql.user.entity

import jakarta.persistence.*
import kr.co.shoppingcart.cart.database.mysql.common.entity.CommonEntity
import kr.co.shoppingcart.cart.database.mysql.user.entity.UserEntity.Companion.USER_ENTITY_NAME

@Entity(name = USER_ENTITY_NAME)
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT", name = "id")
    val id: Long? = null,

    @Column(nullable = true, length = 200)
    val email: String?,

    @Column(nullable = false, length = 30, name = "login_type")
    val loginType: String
): CommonEntity() {
    companion object {
        const val USER_ENTITY_NAME = "user"
    }
}