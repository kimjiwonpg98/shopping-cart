package kr.co.shoppingcart.cart.core.tokenexpiration.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kr.co.shoppingcart.cart.utils.BaseEntity

@Entity(name = "token_expiration")
@Table(name = "token_expiration")
class TokenExpiration(
    @Column(nullable = false, name = "name", length = 50)
    val name: String,
    @Column(nullable = false, name = "ttl")
    val ttl: Long,
    @Column(nullable = false, name = "refresh_ttl")
    val refreshTTL: Long,
) : BaseEntity()
