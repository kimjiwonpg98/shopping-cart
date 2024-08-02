package kr.co.shoppingcart.cart.config.cache

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.Arrays
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

@EnableCaching
@Configuration
class CacheConfig {
    @Bean
    fun cacheManager(): CacheManager {
        val caches: List<CaffeineCache> =
            Arrays
                .stream(CacheType.entries.toTypedArray())
                .map {
                    CaffeineCache(
                        it.cacheName,
                        Caffeine
                            .newBuilder()
                            .recordStats()
                            .maximumSize(
                                it.maximumCachedSize,
                            ).expireAfterWrite(it.expireAfterSeconds, TimeUnit.SECONDS)
                            .build(),
                    )
                }.collect(Collectors.toList())
        val cacheManager = SimpleCacheManager()
        cacheManager.setCaches(caches)

        return cacheManager
    }
}
