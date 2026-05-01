package napetrico.gen.arturroblox.viewmodels

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import napetrico.gen.arturroblox.entities.ShoppingCart
import napetrico.gen.arturroblox.models.CartItemModel
import napetrico.gen.arturroblox.models.ItemModel
import napetrico.gen.arturroblox.models.NewShoppingCart
import napetrico.gen.arturroblox.repositories.CartItemRepository
import napetrico.gen.arturroblox.repositories.ShoppingCartRepository

class CartViewModel {
    private val shoppingCartRepository = ShoppingCartRepository()
    private val cartItemRepository = CartItemRepository()

    // We find the uncompleted cart, if there's none, a new one is created, isCompleted default = false
    var currentCart: ShoppingCart = shoppingCartRepository.findUncompleted()?:
        shoppingCartRepository.create(NewShoppingCart())
    val items: ObservableList<CartItemModel> = FXCollections.observableArrayList()

    init {
        // The items of the uncompleted cart are loaded to be shown on the item-shopping screen
        items.addAll(cartItemRepository.getItemsByCart(currentCart))
    }

    fun createNewShoppingCart() {
        currentCart = shoppingCartRepository.create(NewShoppingCart())
    }

    fun addItem(item: ItemModel) {
        if (items.any { it.itemId == item.id }) return

        val cartItem = CartItemModel(
            item.id,
            item.descriptionProperty.value,
            1,
            item.unitPriceProperty.value
        )
        items.add(cartItem)
        cartItemRepository.add(currentCart.id.value, item.id, 1)
    }

    fun emptyShoppingCart() {
        items.clear()
        cartItemRepository.remove(currentCart.id.value)
    }

    fun removeItem(cartItem: CartItemModel) {
        items.remove(cartItem)
        cartItemRepository.remove(currentCart.id.value, cartItem.itemId)
    }

    fun changeQuantity(cartItem: CartItemModel, delta: Int) {
        val current = cartItem.quantityProperty.get()
        val newQuantity = (current + delta).coerceAtLeast(1)

        cartItem.quantityProperty.set(newQuantity)
        cartItemRepository.updateQuantity(currentCart.id.value, cartItem.itemId, newQuantity)
    }
}