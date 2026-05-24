package napetrico.gen.arturroblox.dsl

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object Categories : IntIdTable("categories") {
    val description = varchar("description", 80)
    val color = varchar("color", 7)
}