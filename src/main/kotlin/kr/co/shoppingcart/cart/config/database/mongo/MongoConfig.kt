package kr.co.shoppingcart.cart.config.database.mongo

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory

@Configuration
@EnableConfigurationProperties(SearchMongoDBProperties::class)
class MongoConfig (
    private val searchMongoDBProperties: SearchMongoDBProperties
) {
    @Bean(name = [SEARCH_MONGODB_CLIENT])
    fun searchMongoClient(): MongoClient {
        return MongoClients.create(searchMongoDBProperties.createConnectionString())
    }

    @Bean(name = [SEARCH_MONGODB_FACTORY])
    fun searchMongoFactory(): SimpleMongoClientDatabaseFactory {
        return SimpleMongoClientDatabaseFactory(searchMongoClient(), searchMongoDBProperties.db)
    }

    @Bean(name = [SEARCH_MONGODB_TEMPLATE])
    fun searchMongoTemplate(): MongoTemplate = MongoTemplate(searchMongoFactory())

    companion object {
        private const val SEARCH_MONGODB_FACTORY = "search-mongodb-factory"
        private const val SEARCH_MONGODB_TEMPLATE = "search-mongodb-template"
        private const val SEARCH_MONGODB_CLIENT = "search-mongodb-client"
    }
}