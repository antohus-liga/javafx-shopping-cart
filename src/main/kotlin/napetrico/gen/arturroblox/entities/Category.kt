package napetrico.gen.arturroblox.entities

import javafx.scene.paint.Color
import napetrico.gen.arturroblox.dsl.Categories
import napetrico.gen.arturroblox.models.CategoryModel
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class Category(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Category>(Categories)

    var description by Categories.description
    var color by Categories.color

    fun toDto() = CategoryModel(id.value, description, Color.web(color))
}