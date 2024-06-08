package kr.co.shoppingcart.cart.config.database.mysql

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["kr.co.shoppingcart"])
@EntityScan(basePackages = ["kr.co.shoppingcart"])
class MysqlConfig
