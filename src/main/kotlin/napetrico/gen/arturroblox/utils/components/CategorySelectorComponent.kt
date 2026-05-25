package napetrico.gen.arturroblox.utils.components

import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.input.MouseButton
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage
import napetrico.gen.arturroblox.models.CategoryModel
import napetrico.gen.arturroblox.viewmodels.CategoryViewModel

class CategorySelectorComponent(categoryViewModel: CategoryViewModel) : VBox() {
    private val listView = ListView(categoryViewModel.categories)

    private val addButton = Button()
    private val confirmButton = Button("Confirmar")

    private var selectedCategory: CategoryModel? = null

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
                        item?.let { category ->
                            val editor = CategoryFormComponent(category)

                            val stage = Stage()

                            stage.initModality(
                                Modality.APPLICATION_MODAL
                            )

                            stage.initOwner(
                                scene.window
                            )

                            stage.title = "Edit Category"

                            stage.scene = Scene(
                                editor,
                                350.0,
                                220.0
                            )

                            stage.showAndWait()

                            // TODO:
                            // Reload categories from repository/viewmodel
                            // Refresh list view if needed
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
            val editor = CategoryFormComponent()

            val stage = Stage()

            stage.initModality(
                Modality.APPLICATION_MODAL
            )

            stage.initOwner(
                scene.window
            )

            stage.title = "Add Category"

            stage.scene = Scene(
                editor,
                350.0,
                220.0
            )

            stage.showAndWait()

            // TODO:
            // Reload categories from repository/viewmodel
            // Refresh list view if needed
        }

        confirmButton.setOnAction {
            selectedCategory?.let {
                // TODO: confirm category selection
            }
        }
    }
}
