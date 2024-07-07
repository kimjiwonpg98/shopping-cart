package kr.co.shoppingcart.cart.database.mongo.search

import kr.co.shoppingcart.cart.database.mongo.search.entity.CartSearchEntity
import org.bson.Document
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class SearchMongoRepositoryImpl (
    @Qualifier("search-mongodb-template")
    private val searchMongoTemplate: MongoTemplate,
): CustomSearchRepository {
    override fun searchForKeyword(keyword: String): List<CartSearchEntity> {
        val collection = searchMongoTemplate.getCollection("search")

        val searchQuery = Document("\$search", Document().apply {
            put("index", "cart_search_index")
            put("compound", Document().apply {
                put("should", listOf(
                    Document("text", Document().apply {
                        put("query", keyword)
                        put("path", "name")
                        put("fuzzy", Document().apply {
                            put("maxEdits", 2)
                            put("prefixLength", 2)
                            put("maxExpansions", 10)
                        })
                    }),
                    Document("text", Document().apply {
                        put("query", keyword)
                        put("path", "category")
                    })
                ))
                put("minimumShouldMatch", 1)
            })
        })

        val pipeline: List<Document> = listOf(searchQuery)

        val results = mutableListOf<CartSearchEntity>()
        collection.aggregate(pipeline).forEach { doc ->
            results.add(searchMongoTemplate.converter.read(CartSearchEntity::class.java, doc))
        }

        return results
    }
}