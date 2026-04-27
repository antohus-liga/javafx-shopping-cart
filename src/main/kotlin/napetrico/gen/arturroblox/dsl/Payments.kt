package napetrico.gen.arturroblox.dsl

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.javatime.date

object Payments : IntIdTable("payments") {
    val total = decimal("total", 11, 2)
    val paymentDate = date("date")
    val relatedCart = reference("cart_id", ShoppingCarts)
}