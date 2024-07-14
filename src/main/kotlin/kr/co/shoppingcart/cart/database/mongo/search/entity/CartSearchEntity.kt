package kr.co.shoppingcart.cart.database.mongo.search.entity

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "search")
class CartSearchEntity(
    @MongoId
    val id: String? = null,
    @Field("name")
    val name: String,
    @Field("category")
    val category: String,
)
