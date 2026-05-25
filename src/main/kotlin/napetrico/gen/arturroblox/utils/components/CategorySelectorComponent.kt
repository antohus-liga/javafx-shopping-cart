package napetrico.gen.arturroblox.utils.components

import javafx.scene.control.*
import javafx.scene.input.MouseButton
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import napetrico.gen.arturroblox.models.CategoryModel
import napetrico.gen.arturroblox.viewmodels.CategoryViewModel

class CategorySelectorComponent(categoryViewModel: CategoryViewModel) : VBox() {
    val listView = ListView(categoryViewModel.categories)

    val addButton = Button()

    val confirmButton = Button("Confirmar")

    var selectedCategory: CategoryModel? = null
        private set

    init {
        spacing = 10.0

        setVgrow(listView, Priority.ALWAYS)

        setupListView()
        setupButtons()

        children.addAll(
            Label("Categorias"),
            listView,
            addButton,
            confirmButton
        )
    }

    private fun setupListView() {
        listView.selectionModel.selectionMode = SelectionMode.SINGLE

        listView.setCellFactory {
            object : ListCell<CategoryModel>() {

                private val contextMenu = ContextMenu()

                init {
                    val editItem = MenuItem("Edit")
                    val deleteItem = MenuItem("Delete")

                    contextMenu.items.addAll(
                        editItem,
                        deleteItem
                    )

                    editItem.setOnAction {
                        item?.let {
                            // TODO: edit category
                        }
                    }

                    deleteItem.setOnAction {
                        item?.let {
                            // TODO: delete category
                        }
                    }

                    setOnMouseClicked { event ->
                        if (item == null) {
                            return@setOnMouseClicked
                        }

                        when (event.button) {
                            MouseButton.PRIMARY -> {

                                selectedCategory = item
                            }

                            MouseButton.SECONDARY -> {

                                contextMenu.show(
                                    this,
                                    event.screenX,
                                    event.screenY
                                )
                            }
                            else -> {}
                        }
                    }
                }

                override fun updateItem(
                    item: CategoryModel?,
                    empty: Boolean
                ) {
                    super.updateItem(item, empty)

                    if (empty || item == null) {
                        text = null
                        graphic = null

                    } else {
                        text = item.descriptionProperty.get()
                    }
                }
            }
        }
    }

    private fun setupButtons() {
        addButton.setOnAction {
            // TODO: add category
        }

        confirmButton.setOnAction {
            selectedCategory?.let {
                // TODO: confirm category selection
            }
        }
    }
}
