package kr.co.shoppingcart.cart.domain.search.command

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class SearchForKeywordCommand(
    @field:NotBlank
    @field:NotNull
    val keyword: String,
) : SelfValidating<SearchForKeywordCommand>() {
    init {
        this.validateSelf()
    }
}
