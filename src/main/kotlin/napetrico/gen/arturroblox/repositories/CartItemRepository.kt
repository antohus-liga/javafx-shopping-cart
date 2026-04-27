package napetrico.gen.arturroblox.repositories

import javafx.beans.property.SimpleIntegerProperty
import napetrico.gen.arturroblox.dsl.CartItems
import napetrico.gen.arturroblox.dsl.Items
import napetrico.gen.arturroblox.models.CartItem
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import java.math.BigDecimal

class CartItemRepository {
    val itemRepository = ItemRepository()

    fun getAll(): List<CartItem> = transaction {
        (CartItems innerJoin Items)
            .selectAll()
            .map {
                CartItem(
                    itemId      = it[CartItems.itemId].value,
                    description = it[Items.description],
                    quantity    = SimpleIntegerProperty(it[CartItems.quantity]),
                    unitPrice   = it[Items.unitPrice]
                )
            }
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

    fun clear(cartId: Int) = transaction {
        CartItems.deleteWhere { CartItems.cartId eq cartId }
    }

    fun getTotal(cartId: Int): BigDecimal = transaction {
        (CartItems innerJoin Items)
            .selectAll().where { CartItems.cartId eq cartId }
            .sumOf { it[Items.unitPrice] * it[CartItems.quantity].toBigDecimal() }
    }
}