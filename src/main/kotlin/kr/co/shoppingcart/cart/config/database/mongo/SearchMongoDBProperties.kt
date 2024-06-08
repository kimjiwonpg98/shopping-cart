package kr.co.shoppingcart.cart.config.database.mongo

import kr.co.shoppingcart.cart.config.database.mongo.SearchMongoDBProperties.Companion.SEARCH_MONGO_DATABASE
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = SEARCH_MONGO_DATABASE)
data class SearchMongoDBProperties(
    val host: String,
    val user: String,
    val password: String,
    val db: String
) {
    fun createConnectionString() =
        "mongodb+srv://$user:$password@$host/?retryWrites=true&w=majority&appName=$db&authSource=admin"

    companion object {
        const val SEARCH_MONGO_DATABASE = "data.mongodb.search"
    }
}
