package kr.co.shoppingcart.cart.config.cache

enum class CacheType(
    val cacheName: String,
    val expireAfterSeconds: Long,
    val maximumCachedSize: Long,
) {
    TEMPLATE_CACHE(cacheName = "template", expireAfterSeconds = 3 * 60 * 1000, maximumCachedSize = 1 * 1024 * 1024),
    TOKEN_CACHE(cacheName = "token", expireAfterSeconds = 60 * 2, maximumCachedSize = 1 * 1024 * 1024),
}
