package napetrico.gen.arturroblox.dsl

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table

object CartItems : Table("cart_items") {
    val id = integer("cart_item_id").autoIncrement()
    val cartId = reference("cart_id", ShoppingCarts)
    val itemId = reference("item_id", Items, ReferenceOption.SET_NULL).nullable()

    val description = varchar("description", 255)
    val unitPrice = decimal("unit_price", 11, 2)

    val quantity = integer("quantity")

    override val primaryKey = PrimaryKey(id)
}