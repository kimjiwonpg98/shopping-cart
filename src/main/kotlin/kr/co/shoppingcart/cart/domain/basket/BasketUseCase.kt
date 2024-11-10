package kr.co.shoppingcart.cart.domain.basket

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.command.CreateBasketCommand
import kr.co.shoppingcart.cart.domain.basket.command.DeleteBasketByIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketByIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketByTemplateIdAndCategoryIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketsByTemplateIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketContentCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketFlagCommand
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.category.CategoryRepository
import kr.co.shoppingcart.cart.domain.permissions.PermissionsRepository
import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BasketUseCase(
    private val basketRepository: BasketRepository,
    private val categoryRepository: CategoryRepository,
    private val templateRepository: TemplateRepository,
    private val permissionsRepository: PermissionsRepository,
) {
    @Transactional
    fun create(createBasketCommand: CreateBasketCommand): Basket {
        val category =
            categoryRepository.getByNameOrBasic(createBasketCommand.categoryName)

        val template =
            templateRepository.getById(createBasketCommand.templateId)
                ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        val permission =
            permissionsRepository.getByUserIdAndTemplateId(
                createBasketCommand.userId,
                createBasketCommand.templateId,
            ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        if (!permission.checkWritePermissionByLevel()) {
            throw CustomException.responseBody(ExceptionCode.E_403_000)
        }

        return basketRepository.save(
            Basket.toDomain(
                id = 0,
                name = createBasketCommand.name,
                checked = createBasketCommand.checked ?: false,
                category = category,
                template = template,
                count = createBasketCommand.count ?: 1,
                createdTime = null,
                updatedTime = null,
                templateId = null,
                categoryId = null,
            ),
            template,
            category,
        )
    }

    @Transactional
    fun updateIsAddedByFlagAndId(updateBasketFlagCommand: UpdateBasketFlagCommand): Basket {
        val basket =
            basketRepository.getById(updateBasketFlagCommand.basketId)
                ?: throw CustomException.responseBody(ExceptionCode.E_404_002)

        val permission =
            permissionsRepository.getByUserIdAndTemplateId(
                updateBasketFlagCommand.userId,
                basket.templateId!!.templateId,
            ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        if (!permission.checkWritePermissionByLevel()) {
            throw CustomException.responseBody(ExceptionCode.E_403_000)
        }

        return this.basketRepository.updateCheckedById(
            updateBasketFlagCommand.basketId,
            updateBasketFlagCommand.checked,
        )
    }

    fun getOwnByTemplateId(command: GetBasketsByTemplateIdCommand): List<Basket> {
        val template =
            templateRepository.getById(command.templateId)
                ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        permissionsRepository.getByUserIdAndTemplateId(
            command.userId,
            template.id.id,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        return this.getByTemplateId(command.templateId)
    }

    @Transactional
    fun updateBasketContent(command: UpdateBasketContentCommand): Basket {
        val basket =
            this.basketRepository.getById(command.basketId)
                ?: throw CustomException.responseBody(ExceptionCode.E_404_002)

        permissionsRepository.getByUserIdAndTemplateId(
            command.userId,
            basket.templateId!!.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        return this.basketRepository.updateContent(
            command.basketId,
            command.content,
            command.count,
        )
    }

    @Transactional
    fun deleteById(command: DeleteBasketByIdCommand) {
        val basket =
            this.basketRepository.getById(command.basketId)
                ?: throw CustomException.responseBody(ExceptionCode.E_404_002)

        val permission =
            permissionsRepository.getByUserIdAndTemplateId(
                command.userId,
                basket.templateId!!.templateId,
            ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        if (!permission.checkWritePermissionByLevel()) throw CustomException.responseBody(ExceptionCode.E_403_000)

        this.basketRepository.deleteById(
            command.basketId,
        )
    }

    fun getByTemplateIdAndCategoryId(command: GetBasketByTemplateIdAndCategoryIdCommand): List<Basket> {
        permissionsRepository.getByUserIdAndTemplateId(
            command.userId,
            command.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        return basketRepository.getByTemplateIdAndCategoryIdByUpdatedDesc(command.templateId, command.categoryId)
    }

    @Transactional(readOnly = true)
    fun getById(command: GetBasketByIdCommand): Basket {
        val basket =
            this.basketRepository.getById(command.basketId)
                ?: throw CustomException.responseBody(ExceptionCode.E_404_002)

        permissionsRepository.getByUserIdAndTemplateId(
            command.userId,
            basket.templateId!!.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        return basket
    }

    @Cacheable(
        value = ["template"],
        key = "#templateId",
    )
    fun getByTemplateIdAndSizeOrderByUpdatedDesc(
        templateId: Long,
        size: Int,
    ): List<Basket> = basketRepository.getByTemplateIdAndSizeOrderByUpdatedDesc(templateId, size)

    private fun getByTemplateId(templateId: Long): List<Basket> = basketRepository.getByTemplateId(templateId)
}
