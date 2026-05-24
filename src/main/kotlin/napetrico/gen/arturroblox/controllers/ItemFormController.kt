package napetrico.gen.arturroblox.controllers

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.control.MenuItem
import javafx.scene.control.TextField
import javafx.scene.paint.Color
import javafx.scene.shape.SVGPath
import javafx.stage.Stage
import napetrico.gen.arturroblox.models.CategoryModel
import napetrico.gen.arturroblox.models.ItemModel
import napetrico.gen.arturroblox.models.NewItem
import napetrico.gen.arturroblox.models.UpdateItem
import napetrico.gen.arturroblox.utils.assets.Assets
import napetrico.gen.arturroblox.viewmodels.CartViewModel
import napetrico.gen.arturroblox.viewmodels.CategoryViewModel
import napetrico.gen.arturroblox.viewmodels.ItemViewModel
import java.math.BigDecimal

class ItemFormController {
    private enum class Mode { CREATE, EDIT }

    @FXML private lateinit var description: TextField
    @FXML private lateinit var unitPrice: TextField
    @FXML private lateinit var categoriesCombo: ComboBox<CategoryModel>

    @FXML private lateinit var descriptionError: Label
    @FXML private lateinit var unitPriceError: Label

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var cartItemViewModel: CartViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var mode: Mode
    private var editingItem: ItemModel? = null


    fun initCreate(itemViewModel: ItemViewModel, categoryViewModel: CategoryViewModel) {
        mode = Mode.CREATE
        this.itemViewModel = itemViewModel
        this.categoryViewModel = categoryViewModel

        categoriesCombo.items.setAll(categoryViewModel.categories)
        categoriesCombo.items.add(CategoryModel(0, "Cool things", Color.web("#000000")))
        categoriesCombo.items.add(CategoryModel(0, "", Color.web("ffffff")))

        initCombo()
    }

    fun initEdit(itemViewModel: ItemViewModel, cartItemViewModel: CartViewModel, categoryViewModel: CategoryViewModel, item: ItemModel) {
        mode = Mode.EDIT
        this.itemViewModel = itemViewModel
        this.cartItemViewModel = cartItemViewModel
        this.categoryViewModel = categoryViewModel
        this.editingItem = item

        description.text = item.descriptionProperty.get()
        unitPrice.text = item.unitPriceProperty.get().toString().replace(".", ",")

        initCombo()
    }

    @FXML
    fun onCancelClick(actionEvent: ActionEvent) {
        val stage = (actionEvent.source as Button).scene.window as Stage
        stage.close()
    }

    @FXML
    fun onConfirmClick(actionEvent: ActionEvent) {
        descriptionError.text = ""
        unitPriceError.text = ""

        val itemDescription = description.text.ifBlank {
            descriptionError.text = "A descrição não pode estar vazia."
            return
        }
        val unitPrice = try {
            val parsed = BigDecimal(unitPrice.text.replace(",", ".").trim())
            if (parsed.scale() > 2) throw NumberFormatException()

            parsed.setScale(2)
        } catch (_: NumberFormatException) {
            unitPriceError.text = "Valor inválido. Exemplo de formato correto: 12.97"
            return
        }

        when (mode) {
            Mode.CREATE -> itemViewModel.addItem(NewItem(itemDescription, unitPrice))
            Mode.EDIT -> {
                itemViewModel.updateItem(editingItem!!, UpdateItem(itemDescription, unitPrice))
                cartItemViewModel.refreshItem(editingItem!!)
            }
        }

        val stage = (actionEvent.source as Button).scene.window as Stage
        stage.close()
    }

    private fun initCombo() {
        categoriesCombo.setCellFactory {

            object : ListCell<CategoryModel>() {

                override fun updateItem(item: CategoryModel?, empty: Boolean) {
                    super.updateItem(item, empty)

                    if (empty || item == null) {
                        text = null
                        contextMenu = null
                        style = ""
                        return
                    }

                    text = item.descriptionProperty.get()

                    val isAddButton = item == categoriesCombo.items.lastOrNull()

                    if (isAddButton) {
                        graphic = SVGPath().apply { content = Assets.PLUS_ICON }
                        style = "-fx-alignment: center;"

                        contextMenu = null

                    } else {
                        style = """-fx-background-color: rgba(
                                    ${(item.colorProperty.get().red * 255).toInt()},
                                    ${(item.colorProperty.get().green * 255).toInt()},
                                    ${(item.colorProperty.get().blue * 255).toInt()},
                                    0.45
                                ); -fx-alignment: center;"""

                        val editItem = MenuItem("Edit")
                        val deleteItem = MenuItem("Delete")

                        editItem.setOnAction {
                            println("Edit ${item.descriptionProperty.get()}")
                        }

                        deleteItem.setOnAction {

//                            categoriesViewModel.remove()

                            println("Deleted ${item.descriptionProperty.get()}")
                        }

                        contextMenu = ContextMenu(editItem, deleteItem)
                    }
                }
            }
        }
    }
}
