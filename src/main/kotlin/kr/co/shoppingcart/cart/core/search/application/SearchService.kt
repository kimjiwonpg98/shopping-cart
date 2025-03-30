package kr.co.shoppingcart.cart.core.search.application

import kr.co.shoppingcart.cart.core.search.application.port.input.SearchByKeyword
import kr.co.shoppingcart.cart.core.search.domain.CartSearch
import org.bson.Document
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class SearchService(
    @Qualifier("search-mongodb-template")
    private val searchMongoTemplate: MongoTemplate,
) : SearchByKeyword {
    override fun search(keyword: String): List<CartSearch> {
        val collection = searchMongoTemplate.getCollection("search")

        val searchQuery =
            Document(
                "\$search",
                Document().apply {
                    put("index", "cart_search_index")
                    put(
                        "compound",
                        Document().apply {
                            put(
                                "should",
                                listOf(
                                    Document(
                                        "text",
                                        Document().apply {
                                            put("query", keyword)
                                            put("path", "name")
                                            put(
                                                "fuzzy",
                                                Document().apply {
                                                    put("maxEdits", 2)
                                                    put("prefixLength", 2)
                                                    put("maxExpansions", 10)
                                                },
                                            )
                                        },
                                    ),
                                    Document(
                                        "text",
                                        Document().apply {
                                            put("query", keyword)
                                            put("path", "category")
                                        },
                                    ),
                                ),
                            )
                            put("minimumShouldMatch", 1)
                        },
                    )
                },
            )

        val pipeline: List<Document> = listOf(searchQuery)

        val results = mutableListOf<CartSearch>()
        collection.aggregate(pipeline).forEach { doc ->
            results.add(searchMongoTemplate.converter.read(CartSearch::class.java, doc))
        }

        return results
    }
}
