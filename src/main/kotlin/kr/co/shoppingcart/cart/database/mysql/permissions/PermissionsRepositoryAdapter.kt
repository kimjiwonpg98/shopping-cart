package kr.co.shoppingcart.cart.database.mysql.permissions

import kr.co.shoppingcart.cart.database.mysql.permissions.entity.PermissionsEntity
import kr.co.shoppingcart.cart.database.mysql.permissions.mapper.PermissionsEntityMapper
import kr.co.shoppingcart.cart.domain.permissions.PermissionsRepository
import kr.co.shoppingcart.cart.domain.permissions.vo.Permissions
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class PermissionsRepositoryAdapter(
    private val permissionsEntityRepository: PermissionsEntityRepository<PermissionsEntity, Long>,
) : PermissionsRepository {
    override fun getByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ): Permissions? =
        permissionsEntityRepository
            .findOneByUserIdAndTemplateId(
                userId,
                templateId,
            )?.let(PermissionsEntityMapper::toDomain)

    @Transactional
    override fun create(permission: Permissions) =
        permissionsEntityRepository
            .save(
                permission.let(PermissionsEntityMapper::toEntity),
            ).let(PermissionsEntityMapper::toDomain)

    @Transactional(propagation = Propagation.MANDATORY)
    override fun createOwnerPermission(
        userId: Long,
        templateId: Long,
    ): Permissions =
        permissionsEntityRepository
            .save(
                PermissionsEntityMapper.toEntity(
                    Permissions.toDomain(
                        userId = userId,
                        templateId = templateId,
                        level = OWNER_LEVEL,
                    ),
                ),
            ).let(PermissionsEntityMapper::toDomain)

    @Transactional(propagation = Propagation.MANDATORY)
    override fun createWriterPermission(
        userId: Long,
        templateId: Long,
    ): Permissions =
        permissionsEntityRepository
            .save(
                PermissionsEntityMapper.toEntity(
                    Permissions.toDomain(
                        userId = userId,
                        templateId = templateId,
                        level = WRITER_LEVEL,
                    ),
                ),
            ).let(PermissionsEntityMapper::toDomain)

    override fun deleteByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ) {
        permissionsEntityRepository.deleteByUserIdAndTemplateId(userId, templateId)
    }

    companion object {
        private const val OWNER_LEVEL = 0
        private const val WRITER_LEVEL = 1
    }
}
