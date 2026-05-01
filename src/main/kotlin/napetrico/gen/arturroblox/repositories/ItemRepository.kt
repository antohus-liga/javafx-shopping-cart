package napetrico.gen.arturroblox.repositories

import napetrico.gen.arturroblox.entities.Item
import napetrico.gen.arturroblox.models.ItemModel
import napetrico.gen.arturroblox.models.NewItem
import napetrico.gen.arturroblox.models.UpdateItem
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class ItemRepository {
    fun getAll(): List<ItemModel> = transaction {
        Item.all().map {
            it.toDto()
        }.toList()
    }

    fun findById(id: Int): ItemModel? = transaction {
        Item.findById(id)?.toDto()
    }

    fun create(item: NewItem): ItemModel = transaction {
        Item.new {
            description = item.description
            unitPrice   = item.unitPrice
        }.toDto()
    }

    fun update(item: Item, updateItem: UpdateItem): ItemModel = transaction {
        updateItem.description?.let { item.description = it }
        updateItem.unitPrice?.let   { item.unitPrice   = it }

        item.toDto()
    }

    fun delete(item: Item): Unit = transaction {
        item.delete()
    }

    fun Item.toDto() = ItemModel(id.value, description, unitPrice)
}