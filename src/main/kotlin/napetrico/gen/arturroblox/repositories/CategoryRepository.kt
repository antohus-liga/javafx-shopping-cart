package napetrico.gen.arturroblox.repositories

import javafx.scene.paint.Color
import napetrico.gen.arturroblox.dsl.Categories
import napetrico.gen.arturroblox.dsl.Items
import napetrico.gen.arturroblox.entities.Category
import napetrico.gen.arturroblox.models.CategoryModel
import napetrico.gen.arturroblox.models.NewCategory
import napetrico.gen.arturroblox.models.UpdateCategory
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update

class CategoryRepository {
    fun getAll(): List<CategoryModel> = transaction {
        Category.all().map {
            it.toDto()
        }.toList()
    }

    fun create(category: NewCategory): CategoryModel = transaction {
        Category.new {
            description = category.description
            color       = category.color.toHex()
        }.toDto()
    }

    fun update(category: CategoryModel, updateCategory: UpdateCategory) = transaction {
        Categories.update( { Categories.id eq category.id }) {
            it[Categories.description] = updateCategory.description
            it[Categories.color] = updateCategory.color.toHex()
        }
    }

    fun delete(category: CategoryModel): Unit = transaction {
        Categories.deleteWhere { Categories.id eq category.id }
    }

    fun Color.toHex(): String =
        "#%02X%02X%02X".format(
            (red * 255).toInt(),
            (green * 255).toInt(),
            (blue * 255).toInt()
        )
}