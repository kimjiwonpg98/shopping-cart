package kr.co.shoppingcart.cart.core.search.application.port.output

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface SearchEntityRepository<T, ID> : MongoRepository<T, ID>
