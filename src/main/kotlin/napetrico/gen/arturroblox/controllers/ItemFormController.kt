package napetrico.gen.arturroblox.controllers

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.stage.Modality
import javafx.stage.Stage
import napetrico.gen.arturroblox.models.ItemModel
import napetrico.gen.arturroblox.models.NewItem
import napetrico.gen.arturroblox.models.UpdateItem
import napetrico.gen.arturroblox.utils.components.CategorySelectorComponent
import napetrico.gen.arturroblox.viewmodels.CartViewModel
import napetrico.gen.arturroblox.viewmodels.CategoryViewModel
import napetrico.gen.arturroblox.viewmodels.ItemViewModel
import java.math.BigDecimal

class ItemFormController {
    private enum class Mode { CREATE, EDIT }

    @FXML private lateinit var description: TextField
    @FXML private lateinit var unitPrice: TextField

    @FXML private lateinit var descriptionError: Label
    @FXML private lateinit var unitPriceError: Label
    @FXML private lateinit var categoryButton: Button

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var cartItemViewModel: CartViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var mode: Mode
    private var editingItem: ItemModel? = null


    fun initCreate(itemViewModel: ItemViewModel, categoryViewModel: CategoryViewModel) {
        mode = Mode.CREATE
        this.itemViewModel = itemViewModel
        this.categoryViewModel = categoryViewModel
        setupCategoryButton()
    }

    fun initEdit(
        itemViewModel: ItemViewModel,
        cartItemViewModel: CartViewModel,
        categoryViewModel: CategoryViewModel,
        item: ItemModel
    ) {
        mode = Mode.EDIT
        this.itemViewModel = itemViewModel
        this.cartItemViewModel = cartItemViewModel
        this.categoryViewModel = categoryViewModel
        this.editingItem = item
        setupCategoryButton()

        description.text = item.descriptionProperty.get()
        unitPrice.text = item.unitPriceProperty.get().toString().replace(".", ",")
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

    private fun setupCategoryButton() {
        categoryButton.onAction = EventHandler {
            val categorySelector = CategorySelectorComponent(categoryViewModel)

            val stage = Stage()

            stage.initModality(Modality.APPLICATION_MODAL)

            stage.title = "Select Category"

            stage.scene = Scene(
                categorySelector,
                300.0,
                400.0
            )

            stage.showAndWait()
        }
    }
}