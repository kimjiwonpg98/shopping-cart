package kr.co.shoppingcart.cart.api.external

import kr.co.shoppingcart.cart.api.external.dto.request.GetKakaoGrantCodeDto
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class ExternalController {
    @GetMapping("/v1/kakao/code")
    fun getKakaoGrantCode(
        @ModelAttribute params: GetKakaoGrantCodeDto,
    ) {
        logger.info { "Getting Kakao Grant Code: $params" }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}
