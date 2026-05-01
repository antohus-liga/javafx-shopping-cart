package napetrico.gen.arturroblox.models

import java.time.LocalDate

data class PaymentModel(
    val id: Int,
    val paymentMethod: String,
    val paymentDate: LocalDate,
    val relatedCart: Int
)