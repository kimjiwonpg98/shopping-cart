package kr.co.shoppingcart.cart.database.mysql.template

import kr.co.shoppingcart.cart.database.mysql.template.entity.TemplateEntity
import kr.co.shoppingcart.cart.database.mysql.template.mapper.TemplateEntityMapper
import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import kr.co.shoppingcart.cart.domain.template.vo.Template
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TemplateRepositoryAdapter(
    private val templateEntityRepository: TemplateEntityRepository<TemplateEntity, Long>,
) : TemplateRepository {
    @Transactional
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

    @Transactional(readOnly = true)
    override fun getById(id: Long): Template? =
        templateEntityRepository.getById(id)?.let(TemplateEntityMapper::toDomain)

    @Transactional(readOnly = true)
    override fun getByIdAndUserId(
        id: Long,
        userId: Long,
    ): Template? = templateEntityRepository.getByIdAndUserId(id, userId)?.let(TemplateEntityMapper::toDomain)

    @Transactional
    override fun updateSharedById(
        id: Long,
        isShared: Boolean,
    ) {
        val template = templateEntityRepository.getById(id)
        template!!.isPublic = isShared
    }
}
