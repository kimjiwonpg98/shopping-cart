package kr.co.shoppingcart.cart.config.cache

enum class CacheType(
    val cacheName: String,
    val expireAfterSeconds: Long,
    val maximumCachedSize: Long,
) {
    BASKET_CACHE(cacheName = "basket", expireAfterSeconds = 3 * 60 * 1000, maximumCachedSize = 1 * 1024 * 1024),
}
