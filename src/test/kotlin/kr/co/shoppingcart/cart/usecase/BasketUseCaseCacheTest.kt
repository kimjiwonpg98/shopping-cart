package kr.co.shoppingcart.cart.usecase

import kr.co.shoppingcart.cart.config.cache.CacheType
import kr.co.shoppingcart.cart.domain.basket.BasketUseCase
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.test.context.TestConstructor

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class BasketUseCaseCacheTest(
    val basketUseCase: BasketUseCase,
    val cacheManager: CacheManager,
) {
    @BeforeEach
    fun setUp() {
        cacheManager.getCache(CacheType.TEMPLATE_CACHE.cacheName)?.clear()
    }

    @Test
    fun `캐시가 존재하면 캐시를 조회`() {
        val first = basketUseCase.getByTemplateIdAndSizeOrderByUpdatedDesc(templateId = testTemplateId, size = size)
        val second = basketUseCase.getByTemplateIdAndSizeOrderByUpdatedDesc(templateId = testTemplateId, size = size)
        val cache = getCacheTemplateId(testTemplateId)
        assertEquals(second, cache)
    }

//    @Test
//    fun `캐시가 존재하지 않으면 캐시해서 가져오지 않는다`() {
//        basketUseCase.getByTemplateIdAndSizeOrderByUpdatedDesc(templateId = testTemplateId, size = size)
//        val cache = getCacheTemplateId(testTemplateId)
//        assertNull(cache)
//    }

    private fun getCacheTemplateId(templateId: Long): List<Basket> =
        cacheManager.getCache(CacheType.TEMPLATE_CACHE.cacheName)?.get(templateId).let { cachedValue ->
            if (cachedValue is Cache.ValueWrapper) {
                val value = cachedValue.get()
                if (value is List<*>) {
                    value.filterIsInstance<Basket>()
                } else {
                    emptyList()
                }
            } else {
                emptyList()
            }
        }

    companion object {
        val testTemplateId: Long = 1
        val size = 3
    }
}
