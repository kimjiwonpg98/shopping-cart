package kr.co.shoppingcart.cart.config.openapi

import io.swagger.v3.oas.models.examples.Example

data class ExampleHolder(
    val example: Example,
    val name: String,
    val code: Int,
    val statusCode: Int,
)
