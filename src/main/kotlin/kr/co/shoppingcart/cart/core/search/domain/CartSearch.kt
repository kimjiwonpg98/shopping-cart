package kr.co.shoppingcart.cart.core.search.domain

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "search")
class CartSearch(
    @MongoId
    val id: String? = null,
    @Field("name")
    val name: String,
    @Field("category")
    val category: String,
)
