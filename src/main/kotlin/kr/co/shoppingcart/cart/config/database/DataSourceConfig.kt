package kr.co.shoppingcart.cart.config.database

import com.zaxxer.hikari.HikariDataSource
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import javax.sql.DataSource

@Configuration
class DataSourceConfig {
    @Bean(name = [CART_WRITE_DATASOURCE])
    @ConfigurationProperties(prefix = CART_WRITE_PROPERTIES)
    fun cartWriteDataSource(): DataSource = DataSourceBuilder.create()
        .type(HikariDataSource::class.java)
        .build()

    @Bean(name = [CART_READ_DATASOURCE])
    @ConfigurationProperties(prefix = CART_READ_PROPERTIES)
    fun cartReadDataSource(): DataSource = DataSourceBuilder.create()
        .type(HikariDataSource::class.java)
        .build()

    @Bean(name = [DYNAMIC_ROUTING_DATA_SOURCE])
    @ConditionalOnBean(name = [CART_WRITE_DATASOURCE, CART_READ_DATASOURCE])
    fun routingDataSource(
        @Qualifier(CART_WRITE_DATASOURCE) cartWriteDataSource: DataSource,
        @Qualifier(CART_READ_DATASOURCE) cartReadDataSource: DataSource
    ): DataSource {
        val dynamicRoutingDataSource = DynamicRoutingDataSource()
        val dataSources: Map<Any, Any> = mapOf(
            DynamicRoutingDataSource.WRITE to cartWriteDataSource,
            DynamicRoutingDataSource.READ to cartReadDataSource
        )
        dynamicRoutingDataSource.setTargetDataSources(dataSources)
        dynamicRoutingDataSource.setDefaultTargetDataSource(cartWriteDataSource)
        return dynamicRoutingDataSource
    }

    @Primary
    @Bean(name = [LAZY_CONNECTION_DATA_SOURCE_PROXY])
    @ConditionalOnBean(name = [DYNAMIC_ROUTING_DATA_SOURCE])
    fun lazyConnectionDataSourceProxy(
        @Qualifier(DYNAMIC_ROUTING_DATA_SOURCE) dynamicRoutingDataSource: DataSource
    ): LazyConnectionDataSourceProxy = LazyConnectionDataSourceProxy(dynamicRoutingDataSource)

    companion object {
        const val CART_WRITE_PROPERTIES = "spring.datasource.cart.write"
        const val CART_READ_PROPERTIES = "spring.datasource.cart.read"

        const val CART_WRITE_DATASOURCE = "cartWriteDataSource"
        const val CART_READ_DATASOURCE = "cartReadDataSource"
        const val DYNAMIC_ROUTING_DATA_SOURCE = "dynamicRoutingDataSource"
        const val LAZY_CONNECTION_DATA_SOURCE_PROXY = "lazyConnectionDataSourceProxy"
        private val logger = KotlinLogging.logger {}
    }
}