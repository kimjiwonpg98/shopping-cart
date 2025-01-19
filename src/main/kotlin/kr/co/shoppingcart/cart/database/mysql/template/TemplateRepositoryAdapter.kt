package kr.co.shoppingcart.cart.database.mysql.template

import kr.co.shoppingcart.cart.database.mysql.template.entity.TemplateEntity
import kr.co.shoppingcart.cart.database.mysql.template.jdbc.TemplateJdbcRepository
import kr.co.shoppingcart.cart.database.mysql.template.mapper.TemplateEntityMapper
import kr.co.shoppingcart.cart.database.mysql.template.mapper.TemplateWithCheckedCountEntityMapper
import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.domain.template.vo.TemplateWithCheckedCount
import org.springframework.stereotype.Component

@Component
class TemplateRepositoryAdapter(
    private val templateEntityRepository: TemplateEntityRepository<TemplateEntity, Long>,
    private val templateJdbcRepository: TemplateJdbcRepository,
) : TemplateRepository {
    override fun create(
        name: String,
        thumbnailIndex: Int,
        userId: Long,
    ): Template =
        templateEntityRepository
            .save(
                TemplateEntity(
                    name = name,
                    thumbnailIdx = thumbnailIndex,
                    userId = userId,
                ),
            ).let(TemplateEntityMapper::toDomain)

    override fun getById(id: Long): Template? = templateEntityRepository.getById(id).let(TemplateEntityMapper::toDomain)

    override fun getByIdWithPercent(id: Long): TemplateWithCheckedCount? =
        templateJdbcRepository.getOneWithCheckedCount(id)?.let {
            TemplateWithCheckedCountEntityMapper.toDomain(it)
        }

    override fun getByIdAndUserId(
        id: Long,
        userId: Long,
    ): Template? = templateEntityRepository.getByIdAndUserId(id, userId)?.let(TemplateEntityMapper::toDomain)

    override fun updateSharedById(
        id: Long,
        isShared: Boolean,
    ): Template {
        val template = templateEntityRepository.getById(id)
        template.isPublic = isShared
        return template.let(TemplateEntityMapper::toDomain)
    }

    override fun update(
        id: Long,
        name: String?,
        thumbnailIndex: Int?,
    ): Template {
        val template = templateEntityRepository.getById(id)
        name?.let { template.name = it }
        thumbnailIndex?.let { template.thumbnailIdx = it }
        return template.let(TemplateEntityMapper::toDomain)
    }

    override fun getWithCompletePercent(userId: Long): List<TemplateWithCheckedCount> =
        templateJdbcRepository.getWithCheckedCount(userId).map {
            TemplateWithCheckedCountEntityMapper.toDomain(it)
        }

    override fun deleteById(id: Long) = templateEntityRepository.deleteById(id)

    override fun deleteByIds(ids: List<Long>) = templateEntityRepository.deleteByIdIn(ids)

    override fun getByUserIdForDefaultName(userId: Long): Template? =
        templateEntityRepository.getByUserIdForDefaultName(userId)?.let(TemplateEntityMapper::toDomain)

    override fun getCountByUserId(userId: Long): Long = templateEntityRepository.countByUserId(userId)
}
