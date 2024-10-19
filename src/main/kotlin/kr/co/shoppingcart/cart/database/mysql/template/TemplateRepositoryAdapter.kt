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
        userId: Long,
    ): Template =
        templateEntityRepository
            .save(
                TemplateEntity(
                    name = name,
                    userId = userId,
                ),
            ).let(TemplateEntityMapper::toDomain)

    override fun getById(id: Long): Template? =
        templateEntityRepository.findById(id)?.let(TemplateEntityMapper::toDomain)

    override fun getByIdAndUserId(
        id: Long,
        userId: Long,
    ): Template? = templateEntityRepository.getByIdAndUserId(id, userId)?.let(TemplateEntityMapper::toDomain)

    override fun updateSharedById(
        id: Long,
        isShared: Boolean,
    ) {
        val template = templateEntityRepository.findById(id)
        template!!.isPublic = isShared
    }

    override fun getWithCompletePercent(
        userId: Long,
        page: Long,
        size: Long,
    ): List<TemplateWithCheckedCount> =
        templateJdbcRepository.getWithCheckedCount(userId, page, size).map {
            TemplateWithCheckedCountEntityMapper.toDomain(it)
        }

    override fun deleteById(id: Long) = templateEntityRepository.deleteById(id)
}
