package kr.co.shoppingcart.cart.database.mysql.tokenExpiration.entity

import jakarta.persistence.*
import kr.co.shoppingcart.cart.database.mysql.common.entity.CommonEntity
import kr.co.shoppingcart.cart.database.mysql.tokenExpiration.entity.TokenExpirationEntity.Companion.TOKEN_EXPIRATION_ENTITY_NAME

@Table(name = TOKEN_EXPIRATION_ENTITY_NAME)
@Entity(name = "tokenExp")
class TokenExpirationEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT", nullable = false, name = "id")
    private val id: Long? = null,

    @Column(nullable = false, name = "name", length = 50)
    val name: String,

    @Column(nullable = false, name = "ttl")
    val ttl: Long,

    @Column(nullable = false, name = "refresh_ttl")
    val refreshTTL: Long,
): CommonEntity() {
    companion object {
        const val TOKEN_EXPIRATION_ENTITY_NAME = "token_expiration"
    }
}