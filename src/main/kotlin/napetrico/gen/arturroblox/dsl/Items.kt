package napetrico.gen.arturroblox.dsl

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object Items : IntIdTable("items") {
    val description = varchar("description", 255)
    val unitPrice = decimal("unit_price", 9, 2)
}