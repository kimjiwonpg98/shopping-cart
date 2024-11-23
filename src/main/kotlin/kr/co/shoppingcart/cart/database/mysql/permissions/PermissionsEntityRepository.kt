package kr.co.shoppingcart.cart.database.mysql.permissions

import kr.co.shoppingcart.cart.database.mysql.permissions.entity.PermissionsEntity
import org.springframework.data.repository.Repository

interface PermissionsEntityRepository<T, ID> : Repository<T, ID> {
    fun findOneByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ): PermissionsEntity?

    fun findByUserId(userId: Long): List<PermissionsEntity>

    fun save(entity: PermissionsEntity): PermissionsEntity

    fun deleteByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    )

    fun deleteByIdIn(ids: List<Long>)
}
