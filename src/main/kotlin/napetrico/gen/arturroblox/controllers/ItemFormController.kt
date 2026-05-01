package napetrico.gen.arturroblox.controllers

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.stage.Stage
import napetrico.gen.arturroblox.models.ItemModel
import napetrico.gen.arturroblox.models.NewItem
import napetrico.gen.arturroblox.models.UpdateItem
import napetrico.gen.arturroblox.viewmodels.CartViewModel
import napetrico.gen.arturroblox.viewmodels.ItemViewModel
import java.math.BigDecimal

class ItemFormController {
    private enum class Mode { CREATE, EDIT }

    @FXML private lateinit var description: TextField
    @FXML private lateinit var unitPrice: TextField

    @FXML private lateinit var descriptionError: Label
    @FXML private lateinit var unitPriceError: Label

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var cartItemViewModel: CartViewModel
    private lateinit var mode: Mode
    private var editingItem: ItemModel? = null

    fun initCreate(itemViewModel: ItemViewModel) {
        mode = Mode.CREATE
        this.itemViewModel = itemViewModel
    }

    fun initEdit(itemViewModel: ItemViewModel, cartItemViewModel: CartViewModel, item: ItemModel) {
        mode = Mode.EDIT
        this.itemViewModel = itemViewModel
        this.cartItemViewModel = cartItemViewModel
        this.editingItem = item

        description.text = item.descriptionProperty.get()
        unitPrice.text = item.unitPriceProperty.get().toString()
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
            descriptionError.text = "Description cannot be blank."
            return
        }
        val unitPrice = try {
            val parsed = BigDecimal(unitPrice.text.trim())
            if (parsed.scale() > 2) throw NumberFormatException()

            parsed.setScale(2)
        } catch (_: NumberFormatException) {
            unitPriceError.text = "Invalid decimal. Correct format example: 12.97"
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
}
