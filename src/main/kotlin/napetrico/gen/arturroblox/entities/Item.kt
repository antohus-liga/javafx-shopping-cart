package napetrico.gen.arturroblox.entities

import napetrico.gen.arturroblox.dsl.Items
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class Item(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Item>(Items)

    var description by Items.description
    var unitPrice by Items.unitPrice
    var category by (Category referencedOn Items.category)
}