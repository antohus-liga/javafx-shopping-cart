package napetrico.gen.arturroblox.repositories

import napetrico.gen.arturroblox.dsl.ShoppingCarts
import napetrico.gen.arturroblox.entities.ShoppingCart
import napetrico.gen.arturroblox.models.NewShoppingCart
import napetrico.gen.arturroblox.models.UpdateShoppingCart
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class ShoppingCartRepository {
    fun getAll() : List<ShoppingCart> = transaction {
        ShoppingCart.all().toList()
    }

    fun findById(id: Int): ShoppingCart? = transaction {
        ShoppingCart.findById(id)
    }

    fun findUncompleted(): ShoppingCart? = transaction {
        ShoppingCart.find { ShoppingCarts.isComplete eq false }.singleOrNull()
    }

    fun create(cart: NewShoppingCart) : ShoppingCart = transaction {
        ShoppingCart.new {
            isComplete = cart.isComplete
        }
    }

    fun update(cart: ShoppingCart, updateCart: UpdateShoppingCart) : ShoppingCart = transaction {
        updateCart.isComplete?.let { cart.isComplete = it }

        cart
    }
}