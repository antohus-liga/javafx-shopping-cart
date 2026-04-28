package napetrico.gen.arturroblox.viewmodels

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import napetrico.gen.arturroblox.entities.ShoppingCart
import napetrico.gen.arturroblox.models.CartItem
import napetrico.gen.arturroblox.models.NewShoppingCart
import napetrico.gen.arturroblox.repositories.CartItemRepository
import napetrico.gen.arturroblox.repositories.ShoppingCartRepository

class CartModel {
    private val shoppingCartRepostory = ShoppingCartRepository()
    private val cartItemRepository = CartItemRepository()

    val cart: ShoppingCart = shoppingCartRepostory.findUncompleted()?:
        shoppingCartRepostory.create(NewShoppingCart(false))
    val items: ObservableList<CartItem> = FXCollections.observableArrayList()

    init {
        items.addAll(cartItemRepository.getItemsByCart(cart))
    }
}