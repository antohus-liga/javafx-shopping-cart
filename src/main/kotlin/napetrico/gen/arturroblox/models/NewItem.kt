package napetrico.gen.arturroblox.models

import java.math.BigDecimal

data class NewItem(
    val description: String,
    val unitPrice: BigDecimal,
    val category: CategoryModel,
)
