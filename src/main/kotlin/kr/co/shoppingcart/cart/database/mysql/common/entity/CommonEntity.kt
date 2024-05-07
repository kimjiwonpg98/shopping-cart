package kr.co.shoppingcart.cart.database.mysql.common.entity

import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class CommonEntity (
    @Column(name = "created_at", updatable = false, nullable = false)
    var createdAt: ZonedDateTime? = null,


    @Column(name = "updated_at", updatable = true, nullable = false)
    var updatedAt: ZonedDateTime? = null
) {
    //    JPA 실행 시
    @PrePersist
    fun prePersist() {
        createdAt = ZonedDateTime.now()
        updatedAt = ZonedDateTime.now()
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = ZonedDateTime.now()
    }
}