package kr.co.shoppingcart.cart.database.mysql.permissions

import kr.co.shoppingcart.cart.database.mysql.permissions.entity.PermissionsEntity

interface PermissionsJpaRepository : PermissionsEntityRepository<PermissionsEntity, Long> {
    override fun findOneByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ): PermissionsEntity?

    override fun save(entity: PermissionsEntity): PermissionsEntity

    override fun deleteByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    )
}
