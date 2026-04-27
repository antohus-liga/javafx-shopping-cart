package napetrico.gen.arturroblox.repositories

import napetrico.gen.arturroblox.entities.Item
import napetrico.gen.arturroblox.models.NewItem
import napetrico.gen.arturroblox.models.UpdateItem
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class ItemRepository {
    fun getAll(): List<Item> = transaction {
        Item.all().toList()
    }

    fun findById(id: Int): Item? = transaction {
        Item.findById(id)
    }

    fun create(item: NewItem): Item = transaction {
        Item.new {
            description = item.description
            unitPrice   = item.unitPrice
        }
    }

    fun update(item: Item, updateItem: UpdateItem): Item = transaction {
        updateItem.description?.let { item.description = it }
        updateItem.unitPrice?.let   { item.unitPrice   = it }

        item
    }

    fun delete(item: Item): Unit = transaction {
        item.delete()
    }
}