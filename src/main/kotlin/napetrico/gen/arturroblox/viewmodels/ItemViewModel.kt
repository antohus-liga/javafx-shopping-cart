package napetrico.gen.arturroblox.viewmodels

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import napetrico.gen.arturroblox.models.ItemModel
import napetrico.gen.arturroblox.models.NewItem
import napetrico.gen.arturroblox.models.UpdateItem
import napetrico.gen.arturroblox.repositories.ItemRepository

class ItemViewModel {
    private val itemRepository = ItemRepository()

    val items: ObservableList<ItemModel> = FXCollections.observableArrayList()

    init {
        items.addAll(itemRepository.getAll())
    }

    fun addItem(newItem: NewItem) {
        val item = itemRepository.create(newItem)
        items.add(item)
    }

    fun updateItem(item: ItemModel, updateItem: UpdateItem) {
        item.descriptionProperty.set(updateItem.description)
        item.unitPriceProperty.set(updateItem.unitPrice)
        item.categoryProperty.set(updateItem.category)
        itemRepository.update(item, updateItem)
    }

    fun remove(item: ItemModel) {
        items.remove(item)
        itemRepository.delete(item)
    }
}