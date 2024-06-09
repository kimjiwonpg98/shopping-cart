package kr.co.shoppingcart.cart.config.database.mongo

import kr.co.shoppingcart.cart.config.database.mongo.SearchMongoDBProperties.Companion.SEARCH_MONGO_DATABASE
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = SEARCH_MONGO_DATABASE)
data class SearchMongoDBProperties(
    private val host: String,
    private val user: String,
    private val password: String,
    private val db: String
) {
    fun createConnectionString(): String =
        "mongodb+srv://$user:$password@$host/?retryWrites=true&w=majority&appName=$db&authSource=admin"

    fun getDB(): String = db

    companion object {
        const val SEARCH_MONGO_DATABASE = "spring.data.mongodb.search"
    }
}
