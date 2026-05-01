package napetrico.gen.arturroblox.viewmodels

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import napetrico.gen.arturroblox.entities.ShoppingCart
import napetrico.gen.arturroblox.models.CartItemModel
import napetrico.gen.arturroblox.models.ItemModel
import napetrico.gen.arturroblox.models.NewShoppingCart
import napetrico.gen.arturroblox.models.UpdateShoppingCart
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
        items.addAll(cartItemRepository.getItemsByCart(currentCart.id.value))
    }

    fun createNewShoppingCart() {
        shoppingCartRepository.update(currentCart, UpdateShoppingCart(true))
        currentCart = shoppingCartRepository.create(NewShoppingCart())
        items.clear()
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
        cartItemRepository.create(currentCart.id.value, item.id, 1)
    }

    fun emptyShoppingCart() {
        items.clear()
        cartItemRepository.clear(currentCart.id.value)
    }

    fun removeItem(cartItem: CartItemModel) {
        items.remove(cartItem)
        cartItemRepository.remove(currentCart.id.value, cartItem.itemId)
    }

    fun removeItem(item: ItemModel) {
        val cartItem = items.find { it.itemId == item.id } ?: return
        items.remove(cartItem)
        cartItemRepository.remove(currentCart.id.value, item.id)
    }

    fun changeQuantity(cartItem: CartItemModel, delta: Int) {
        val current = cartItem.quantityProperty.get()
        val newQuantity = (current + delta).coerceAtLeast(1)

        cartItem.quantityProperty.set(newQuantity)
        cartItemRepository.updateQuantity(currentCart.id.value, cartItem.itemId, newQuantity)
    }

    fun refreshItem(item: ItemModel) {
        items.find { it.itemId == item.id }?.let {
            it.descriptionProperty.set(item.descriptionProperty.value)
            it.unitPriceProperty.set(item.unitPriceProperty.get())
        }
    }

    fun getCartTotal() = items.sumOf { it.totalPriceProperty.get() }
}