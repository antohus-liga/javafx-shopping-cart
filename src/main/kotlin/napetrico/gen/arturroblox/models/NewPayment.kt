package napetrico.gen.arturroblox.models

import java.math.BigDecimal
import java.time.LocalDate

data class NewPayment(
    val paymentMethod: String,
    val total: BigDecimal,
    val relatedCart: Int,
    val paymentDate: LocalDate = LocalDate.now(),
)
