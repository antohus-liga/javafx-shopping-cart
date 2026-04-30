package napetrico.gen.arturroblox.repositories

import napetrico.gen.arturroblox.dsl.CartItems
import napetrico.gen.arturroblox.dsl.Items
import napetrico.gen.arturroblox.entities.ShoppingCart
import napetrico.gen.arturroblox.models.CartItem
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import java.math.BigDecimal

class CartItemRepository {
    fun getAll(): List<CartItem> = transaction {
        (CartItems innerJoin Items)
            .selectAll()
            .map { toDto(it) }
    }

    fun getItemsByCart(cart: ShoppingCart): List<CartItem> = transaction {
        (CartItems innerJoin Items)
            .selectAll().where { CartItems.cartId eq cart.id }
            .map { toDto(it) }
    }

    fun add(cartId: Int, itemId: Int, quantity: Int) = transaction {
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

    fun remove(cartId: Int) = transaction {
        CartItems.deleteWhere { CartItems.cartId eq cartId }
    }

    fun clear(cartId: Int) = transaction {
        CartItems.deleteWhere { CartItems.cartId eq cartId }
    }

    fun getTotal(cartId: Int): BigDecimal = transaction {
        (CartItems innerJoin Items)
            .selectAll().where { CartItems.cartId eq cartId }
            .sumOf { it[Items.unitPrice] * it[CartItems.quantity].toBigDecimal() }
    }

    fun toDto(row: ResultRow): CartItem = CartItem(
        itemId      = row[CartItems.itemId].value,
        description = row[Items.description],
        quantity    = row[CartItems.quantity],
        unitPrice   = row[Items.unitPrice]
    )
}