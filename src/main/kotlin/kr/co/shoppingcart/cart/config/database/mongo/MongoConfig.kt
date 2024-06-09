package kr.co.shoppingcart.cart.config.database.mongo

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@ConfigurationPropertiesScan("kr.co.shoppingcart.cart.config.database.mongo")
@EnableMongoRepositories(
    basePackages = ["kr.co.shoppingcart.cart"],
    mongoTemplateRef = "search-mongodb-template"
)
class MongoConfig