package napetrico.gen.arturroblox.viewmodels

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import napetrico.gen.arturroblox.entities.ShoppingCart
import napetrico.gen.arturroblox.models.CartItem
import napetrico.gen.arturroblox.models.NewShoppingCart
import napetrico.gen.arturroblox.repositories.CartItemRepository
import napetrico.gen.arturroblox.repositories.ShoppingCartRepository

class CartModel {
    private val shoppingCartRepository = ShoppingCartRepository()
    private val cartItemRepository = CartItemRepository()

    // We find the uncompleted cart, if there's none, a new one is creted
    var currentCart: ShoppingCart = shoppingCartRepository.findUncompleted()?:
        shoppingCartRepository.create(NewShoppingCart(false))
    val items: ObservableList<CartItem> = FXCollections.observableArrayList()

    init {
        // The items of the uncompleted cart are loaded to be shown on the item-shopping screen
        items.addAll(cartItemRepository.getItemsByCart(currentCart))
    }

    fun createNewShoppingCart() {
        currentCart = shoppingCartRepository.create(NewShoppingCart(false))
    }
}