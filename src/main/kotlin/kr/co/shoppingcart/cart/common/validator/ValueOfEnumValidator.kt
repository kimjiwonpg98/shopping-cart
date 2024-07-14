package kr.co.shoppingcart.cart.common.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ValueOfEnumValidator : ConstraintValidator<ValueOfEnum, CharSequence?> {
    private var acceptedValues: List<String>? = null

    override fun initialize(annotation: ValueOfEnum) {
        acceptedValues = annotation.enumClass.java.enumConstants.map { it.name }
    }

    override fun isValid(
        value: CharSequence?,
        context: ConstraintValidatorContext,
    ): Boolean {
        if (value == null) return true
        return acceptedValues!!.contains(value.toString())
    }
}
