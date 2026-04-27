package napetrico.gen.arturroblox.models

import napetrico.gen.arturroblox.entities.ShoppingCart
import java.math.BigDecimal
import java.time.LocalDate

data class NewPayment(
    val total: BigDecimal,
    val paymentDate: LocalDate,
    val relatedCart: ShoppingCart
)
