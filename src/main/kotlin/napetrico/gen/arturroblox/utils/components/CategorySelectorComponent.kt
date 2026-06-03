package napetrico.gen.arturroblox.utils.components

import javafx.scene.control.*
import javafx.scene.input.MouseButton
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage
import napetrico.gen.arturroblox.models.CategoryModel
import napetrico.gen.arturroblox.utils.extensions.toStyledScene
import napetrico.gen.arturroblox.viewmodels.CategoryViewModel

class CategorySelectorComponent(private val categoryViewModel: CategoryViewModel) : VBox() {
    private val listView = ListView(categoryViewModel.categories)

    private val addButton = Button("Adicionar")
    private val confirmButton = Button("Confirmar")

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
                    val editItem = MenuItem("Editar")
                    val deleteItem = MenuItem("Remover")

                    contextMenu.items.addAll(
                        editItem,
                        deleteItem
                    )

                    editItem.setOnAction {
                        item?.let { category ->
                            val editor = CategoryFormComponent(category, categoryViewModel)

                            Stage().apply {
                                initModality(Modality.APPLICATION_MODAL)
                                title = "Editar Categoria"
                                scene = editor.toStyledScene().apply {
                                    root = editor
                                    minWidth = 300.0
                                    minHeight = 400.0
                                }
                                showAndWait()
                            }
                        }
                    }

                    deleteItem.setOnAction {
                        item?.let {
                            categoryViewModel.delete(item)
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
                        style = """-fx-background-color: rgba(
                                    ${(item.colorProperty.get().red * 255).toInt()},
                                    ${(item.colorProperty.get().green * 255).toInt()},
                                    ${(item.colorProperty.get().blue * 255).toInt()},
                                    0.4
                                );"""
                    }
                }
            }
        }
    }

    private fun setupButtons() {
        addButton.setOnAction {
            val editor = CategoryFormComponent(categoryViewModel = categoryViewModel)

            Stage().apply {
                initModality(Modality.APPLICATION_MODAL)
                title = "Adicionar Categoria"
                scene = editor.toStyledScene().apply {
                    root = editor
                    minWidth = 300.0
                    minHeight = 400.0
                }
                showAndWait()
            }

            listView.refresh()
        }

        confirmButton.setOnAction {
            selectedCategory?.let {
                scene?.window?.let {
                    (it as Stage).close()
                }
            }
        }
    }
}
