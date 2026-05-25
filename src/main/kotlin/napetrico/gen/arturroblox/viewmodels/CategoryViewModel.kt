package napetrico.gen.arturroblox.viewmodels

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import napetrico.gen.arturroblox.models.CategoryModel
import napetrico.gen.arturroblox.models.NewCategory
import napetrico.gen.arturroblox.models.UpdateCategory
import napetrico.gen.arturroblox.repositories.CategoryRepository

class CategoryViewModel {
    private val categoryRepository = CategoryRepository()

    val categories: ObservableList<CategoryModel> = FXCollections.observableArrayList()

    init {
        categories.setAll(categoryRepository.getAll())
    }

    fun create(newCategory: NewCategory) {
        val category = categoryRepository.create(newCategory)
        categories.add(category)
    }

    fun update(category: CategoryModel, updateCategory: UpdateCategory) {
        categoryRepository.update(category, updateCategory)
        category.descriptionProperty.set(updateCategory.description)
        category.colorProperty.set(updateCategory.color)
    }

    fun delete(category: CategoryModel) {
        categoryRepository.delete(category)
        categories.remove(category)
    }
}