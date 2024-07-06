package kr.co.shoppingcart.cart.database.mongo.search

import kr.co.shoppingcart.cart.database.mongo.search.entity.CartSearchEntity
import org.bson.Document
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import java.util.*

fun documentOf(vararg pairs: Pair<String, Any?>) = Document(mapOf(*pairs))

@Component
class SearchMongoRepositoryImpl (
    @Qualifier("search-mongodb-template")
    private val searchMongoTemplate: MongoTemplate,
): CustomSearchRepository {
    override fun searchForKeyword(keyword: String): List<CartSearchEntity> {
        val collection = searchMongoTemplate.getCollection("search")

        val searchStage = documentOf(
            "\$search" to documentOf(
                "requestId" to UUID.randomUUID().toString(),
                "index" to "cart_search_index",
                "compound" to documentOf(
                    "should" to listOf(
                        documentOf(
                            "text" to documentOf(
                                "query" to keyword,
                                "path" to "name",
                                "fuzzy" to documentOf(
                                    "maxEdits" to 2,
                                    "prefixLength" to 2,
                                    "maxExpansions" to 10
                                )
                            )
                        ),
                        documentOf(
                            "text" to documentOf(
                                "query" to keyword,
                                "path" to "category",
                            )
                        ),
                    ),
                    "minimumShouldMatch" to 1
                )
            )
        )

        val pipeline: List<Document> = listOf(searchStage)

        val results = mutableListOf<CartSearchEntity>()
        collection.aggregate(pipeline).forEach { doc ->
            results.add(searchMongoTemplate.converter.read(CartSearchEntity::class.java, doc))
        }

        return results
    }
}