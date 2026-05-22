package napetrico.gen.arturroblox.dsl

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object Items : IntIdTable("items") {
    val description = varchar("description", 255)
    val unitPrice = decimal("unit_price", 9, 2)
    val category = optReference("category", Categories, onDelete = ReferenceOption.SET_NULL)
}