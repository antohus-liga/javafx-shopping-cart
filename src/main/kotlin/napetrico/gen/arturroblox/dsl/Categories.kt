package napetrico.gen.arturroblox.dsl

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object Categories : IntIdTable("users") {
    val description = varchar("username", 80)
    val color = varchar("password", 7)
}