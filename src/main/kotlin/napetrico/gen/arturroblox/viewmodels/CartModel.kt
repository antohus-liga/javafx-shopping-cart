package napetrico.gen.arturroblox.viewmodels

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import napetrico.gen.arturroblox.entities.ShoppingCart
import napetrico.gen.arturroblox.models.CartItem
import napetrico.gen.arturroblox.models.NewShoppingCart
import napetrico.gen.arturroblox.repositories.CartItemRepository
import napetrico.gen.arturroblox.repositories.ShoppingCartRepository
import kotlin.math.absoluteValue

class CartModel {
    private val shoppingCartRepository = ShoppingCartRepository()
    private val cartItemRepository = CartItemRepository()

    // We find the uncompleted cart, if there's none, a new one is created, isCompleted default = false
    var currentCart: ShoppingCart = shoppingCartRepository.findUncompleted()?:
        shoppingCartRepository.create(NewShoppingCart())
    val items: ObservableList<CartItem> = FXCollections.observableArrayList()

    init {
        // The items of the uncompleted cart are loaded to be shown on the item-shopping screen
        items.addAll(cartItemRepository.getItemsByCart(currentCart))
    }

    fun createNewShoppingCart() {
        currentCart = shoppingCartRepository.create(NewShoppingCart())
    }

    fun emptyShoppingCart() {
        items.clear()
//        cartItemRepository.remove(currentCart.id.value) // this line is commented for testing purposes
    }

    fun removeItem(cartItem: CartItem) {
        items.remove(cartItem)
//        cartItemRepository.remove(currentCart.id.value, cartItem.itemId) // this line is commented for testing purposes
    }

    fun changeQuantity(cartItem: CartItem, delta: Int) {
        val current = cartItem.quantityProperty.get()
        val newQuantity = (current + delta).coerceAtLeast(1)

        cartItem.quantityProperty.set(newQuantity)
        cartItemRepository.updateQuantity(currentCart.id.value, cartItem.itemId, newQuantity)
    }
}