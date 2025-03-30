package kr.co.shoppingcart.cart.utils

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.ZonedDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT", nullable = false, name = "id")
    val id: Long? = 0L,
    @Column(name = "created_at", updatable = false, nullable = false)
    var createdAt: ZonedDateTime? = null,
    @Column(name = "updated_at", updatable = true, nullable = false)
    var updatedAt: ZonedDateTime? = null,
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other == null || javaClass != other.javaClass) return false

        val that = other as BaseEntity
        return id == that.id
    }

    override fun hashCode(): Int = id.hashCode()

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
