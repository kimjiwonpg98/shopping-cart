package kr.co.cart.config

import com.zaxxer.hikari.HikariDataSource
import kr.co.title.config.DynamicRoutingDataSource
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
    @ConfigurationProperties(prefix = TITLE_ACADEMY_WRITE_PROPERTIES)
    fun titleAcademyWriteDataSource(): DataSource = DataSourceBuilder.create()
        .type(HikariDataSource::class.java)
        .build()

    @Bean(name = [CART_READ_DATASOURCE])
    @ConfigurationProperties(prefix = TITLE_ACADEMY_READ_PROPERTIES)
    fun titleAcademyReadDataSource(): DataSource = DataSourceBuilder.create()
        .type(HikariDataSource::class.java)
        .build()

    @Bean(name = [DYNAMIC_ROUTING_DATA_SOURCE])
    @ConditionalOnBean(name = [CART_WRITE_DATASOURCE, CART_READ_DATASOURCE])
    fun routingDataSource(
        @Qualifier(CART_WRITE_DATASOURCE) titleAcademyWriteDataSource: DataSource,
        @Qualifier(CART_READ_DATASOURCE) titleAcademyReadDataSource: DataSource
    ): DataSource {
        val dynamicRoutingDataSource = DynamicRoutingDataSource()
        val dataSources: Map<Any, Any> = mapOf(
            DynamicRoutingDataSource.WRITE to titleAcademyWriteDataSource,
            DynamicRoutingDataSource.READ to titleAcademyReadDataSource
        )
        dynamicRoutingDataSource.setTargetDataSources(dataSources)
        dynamicRoutingDataSource.setDefaultTargetDataSource(titleAcademyWriteDataSource)
        return dynamicRoutingDataSource
    }

    @Primary
    @Bean(name = [LAZY_CONNECTION_DATA_SOURCE_PROXY])
    @ConditionalOnBean(name = [DYNAMIC_ROUTING_DATA_SOURCE])
    fun lazyConnectionDataSourceProxy(
        @Qualifier(DYNAMIC_ROUTING_DATA_SOURCE) dynamicRoutingDataSource: DataSource
    ): LazyConnectionDataSourceProxy = LazyConnectionDataSourceProxy(dynamicRoutingDataSource)

    companion object {
        const val TITLE_ACADEMY_WRITE_PROPERTIES = "spring.datasource.title-academy.write"
        const val TITLE_ACADEMY_READ_PROPERTIES = "spring.datasource.title-academy.read"

        const val CART_WRITE_DATASOURCE = "cartWriteDataSource"
        const val CART_READ_DATASOURCE = "cartReadDataSource"
        const val DYNAMIC_ROUTING_DATA_SOURCE = "dynamicRoutingDataSource"
        const val LAZY_CONNECTION_DATA_SOURCE_PROXY = "lazyConnectionDataSourceProxy"
        private val logger = KotlinLogging.logger {}
    }
}