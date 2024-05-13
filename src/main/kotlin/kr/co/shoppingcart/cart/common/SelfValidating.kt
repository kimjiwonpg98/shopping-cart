package kr.co.shoppingcart.cart.common

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validation
import jakarta.validation.Validator

abstract class SelfValidating<T> {
    private val validator: Validator

    init {
        val factory = Validation.buildDefaultValidatorFactory()
        validator = factory.validator
    }

    protected fun validateSelf() {
        val violations = validator.validate(this as T)
        if (!violations.isEmpty()) {
            throw ConstraintViolationException(violations)
        }
    }
}