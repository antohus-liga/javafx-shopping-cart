package napetrico.gen.arturroblox.viewmodels

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import napetrico.gen.arturroblox.models.ItemModel
import napetrico.gen.arturroblox.repositories.CartItemRepository
import napetrico.gen.arturroblox.repositories.ItemRepository

class ItemViewModel {
    private val itemRepository = ItemRepository()
    private val cartItemRepository = CartItemRepository()

    val items: ObservableList<ItemModel> = FXCollections.observableArrayList()

    init {
        items.addAll(itemRepository.getAll())
    }
}