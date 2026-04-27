package napetrico.gen.arturroblox.dsl

import org.jetbrains.exposed.v1.core.Table

object CartItems : Table("cart_items") {
    val cartId = reference("cart_id", ShoppingCarts)
    val itemId = reference("item_id", Items)
    val quantity = integer("quantity")

    override val primaryKey = PrimaryKey(cartId, itemId)
}