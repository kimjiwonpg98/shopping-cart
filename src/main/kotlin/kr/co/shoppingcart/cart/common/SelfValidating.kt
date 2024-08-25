package kr.co.shoppingcart.cart.common

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import jakarta.validation.Validator
import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import java.util.stream.Collectors

abstract class SelfValidating<T> {
    private val validator: Validator

    init {
        val factory = Validation.buildDefaultValidatorFactory()
        validator = factory.validator
    }

    protected fun validateSelf() {
        val violations = validator.validate(this as T)
        if (violations.isNotEmpty()) {
            throw CustomException.responseBody(
                code = ExceptionCode.E_400_000,
                detailInformation = this.detailToString(violations),
            )
        }
    }

    private fun detailToString(constraintViolation: Set<ConstraintViolation<*>?>): String =
        constraintViolation
            .stream()
            .map { violation ->
                if (violation == null) "null" else violation.propertyPath.toString() + ":" + violation.message
            }.collect(Collectors.joining(", "))
}
