package kr.co.cart.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["kr.co.cart"])
@EntityScan(basePackages = ["kr.co.cart.entity"])
@ComponentScan(basePackages = ["kr.co.cart"])
class MysqlConfig
