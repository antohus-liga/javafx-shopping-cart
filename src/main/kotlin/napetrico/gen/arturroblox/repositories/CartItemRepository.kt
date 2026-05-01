package napetrico.gen.arturroblox.repositories

import napetrico.gen.arturroblox.dsl.CartItems
import napetrico.gen.arturroblox.dsl.Items
import napetrico.gen.arturroblox.entities.ShoppingCart
import napetrico.gen.arturroblox.models.CartItemModel
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update

class CartItemRepository {
    fun getAll(): List<CartItemModel> = transaction {
        (CartItems innerJoin Items)
            .selectAll()
            .map { it.toDto() }
    }

    fun getItemsByCart(cartId: Int): List<CartItemModel> = transaction {
        (CartItems innerJoin Items)
            .selectAll().where { CartItems.cartId eq cartId }
            .map { it.toDto() }
    }

    fun create(cartId: Int, itemId: Int, quantity: Int) = transaction {
        CartItems.insert {
            it[CartItems.cartId] = cartId
            it[CartItems.itemId] = itemId
            it[CartItems.quantity] = quantity
        }
    }

    fun updateQuantity(cartId: Int, itemId: Int, quantity: Int) = transaction {
        CartItems.update({ (CartItems.cartId eq cartId) and (CartItems.itemId eq itemId) }) {
            it[CartItems.quantity] = quantity
        }
    }

    fun remove(cartId: Int, itemId: Int) = transaction {
        CartItems.deleteWhere { (CartItems.cartId eq cartId) and (CartItems.itemId eq itemId) }
    }

    fun clear(cartId: Int) = transaction {
        CartItems.deleteWhere { CartItems.cartId eq cartId }
    }

    fun ResultRow.toDto(): CartItemModel = CartItemModel(
        itemId      = this[CartItems.itemId].value,
        description = this[Items.description],
        quantity    = this[CartItems.quantity],
        unitPrice   = this[Items.unitPrice]
    )
}