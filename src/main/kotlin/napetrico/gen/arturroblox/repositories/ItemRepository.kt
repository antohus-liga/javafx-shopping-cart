package napetrico.gen.arturroblox.repositories

import napetrico.gen.arturroblox.dsl.Items
import napetrico.gen.arturroblox.entities.Item
import napetrico.gen.arturroblox.models.ItemModel
import napetrico.gen.arturroblox.models.NewItem
import napetrico.gen.arturroblox.models.UpdateItem
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update

class ItemRepository {
    fun getAll(): List<ItemModel> = transaction {
        Item.all().map {
            it.toDto()
        }.toList()
    }

    fun create(item: NewItem): ItemModel = transaction {
        Item.new {
            description = item.description
            unitPrice   = item.unitPrice
        }.toDto()
    }

    fun update(item: ItemModel, updateItem: UpdateItem) = transaction {
        Items.update( { Items.id eq item.id }) {
            it[Items.description] = updateItem.description
            it[Items.unitPrice] = updateItem.unitPrice
        }
    }

    fun delete(item: ItemModel): Unit = transaction {
        Items.deleteWhere { Items.id eq item.id }
    }

    fun Item.toDto() = ItemModel(id.value, description, unitPrice)
}