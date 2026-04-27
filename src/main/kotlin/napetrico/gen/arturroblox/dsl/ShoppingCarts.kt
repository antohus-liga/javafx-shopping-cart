package napetrico.gen.arturroblox.dsl

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object ShoppingCarts : IntIdTable("shopping_carts") {
    val isComplete = bool("is_complete").default(false)
}