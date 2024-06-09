package kr.co.shoppingcart.cart.domain.search.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class SearchForKeywordCommand (
    @NotNull val keyword: String,
): SelfValidating<SearchForKeywordCommand>() {
    init {
        this.validateSelf()
    }
}