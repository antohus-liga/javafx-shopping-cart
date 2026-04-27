package napetrico.gen.arturroblox.entities

import napetrico.gen.arturroblox.dsl.ShoppingCarts
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class ShoppingCart(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ShoppingCart>(ShoppingCarts)

    var isComplete by ShoppingCarts.isComplete
}