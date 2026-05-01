package napetrico.gen.arturroblox.dsl

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table

object CartItems : Table("cart_items") {
    val cartId = reference("cart_id", ShoppingCarts)
    val itemId = reference("item_id", Items, ReferenceOption.CASCADE)
    val quantity = integer("quantity")

    override val primaryKey = PrimaryKey(cartId, itemId)
}