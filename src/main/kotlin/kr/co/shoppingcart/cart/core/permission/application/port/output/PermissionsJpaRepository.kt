package kr.co.shoppingcart.cart.core.permission.application.port.output

import kr.co.shoppingcart.cart.core.permission.domain.Permission

interface PermissionsJpaRepository : PermissionsRepository<Permission, Long> {
    override fun findOneByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ): Permission?

    override fun findByUserId(userId: Long): List<Permission>

    override fun save(entity: Permission): Permission

    override fun deleteByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    )

    override fun deleteByIdIn(ids: List<Long>)
}
