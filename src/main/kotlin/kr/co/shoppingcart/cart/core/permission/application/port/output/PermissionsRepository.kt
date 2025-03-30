package kr.co.shoppingcart.cart.core.permission.application.port.output

import kr.co.shoppingcart.cart.core.permission.domain.Permission
import org.springframework.data.repository.Repository

interface PermissionsRepository<T, ID> : Repository<T, ID> {
    fun findOneByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ): Permission?

    fun findByUserId(userId: Long): List<Permission>

    fun save(entity: Permission): Permission

    fun deleteByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    )

    fun deleteByIdIn(ids: List<Long>)
}
