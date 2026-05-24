package napetrico.gen.arturroblox.viewmodels

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import napetrico.gen.arturroblox.models.CategoryModel
import napetrico.gen.arturroblox.repositories.CategoryRepository

class CategoryViewModel {
    private val categoryRepository = CategoryRepository()

    val categories: ObservableList<CategoryModel> = FXCollections.observableArrayList()


    init {
        categories.setAll(categoryRepository.getAll())
    }
}