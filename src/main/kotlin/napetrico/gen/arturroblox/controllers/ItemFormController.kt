package napetrico.gen.arturroblox.controllers

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.stage.Modality
import javafx.stage.Stage
import napetrico.gen.arturroblox.models.CategoryModel
import napetrico.gen.arturroblox.models.ItemModel
import napetrico.gen.arturroblox.models.NewItem
import napetrico.gen.arturroblox.models.UpdateItem
import napetrico.gen.arturroblox.utils.components.CategorySelectorComponent
import napetrico.gen.arturroblox.utils.extensions.toStyledScene
import napetrico.gen.arturroblox.viewmodels.CartViewModel
import napetrico.gen.arturroblox.viewmodels.CategoryViewModel
import napetrico.gen.arturroblox.viewmodels.ItemViewModel
import java.math.BigDecimal
import kotlin.text.get
import kotlin.text.toInt
import kotlin.times

class ItemFormController {
    private enum class Mode { CREATE, EDIT }

    @FXML private lateinit var description: TextField
    @FXML private lateinit var unitPrice: TextField

    @FXML private lateinit var descriptionError: Label
    @FXML private lateinit var unitPriceError: Label
    @FXML private lateinit var categoryError: Label
    @FXML private lateinit var categoryButton: Button

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var cartItemViewModel: CartViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var mode: Mode
    private var editingItem: ItemModel? = null
    private var selectedCategory: CategoryModel? = null


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
        categoryError.text = ""

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

        if (selectedCategory == null) {
            categoryError.text = "Selecione uma categoria."
            return
        }

        when (mode) {
            Mode.CREATE -> itemViewModel.addItem(NewItem(itemDescription, unitPrice, selectedCategory!!))
            Mode.EDIT -> {
                itemViewModel.updateItem(editingItem!!, UpdateItem(itemDescription, unitPrice, selectedCategory!!))
                cartItemViewModel.refreshItem(editingItem!!)
            }
        }

        val stage = (actionEvent.source as Button).scene.window as Stage
        stage.close()
    }

    private fun setupCategoryButton() {
        categoryButton.onAction = EventHandler {
            val categorySelector = CategorySelectorComponent(categoryViewModel)

            Stage().apply {
                initModality(Modality.APPLICATION_MODAL)
                title = "Select Category"
                scene = categorySelector.toStyledScene().apply {
                    root = categorySelector
                    minWidth = 300.0
                    minHeight = 400.0
                }
                showAndWait()
            }

            selectedCategory = categorySelector.selectedCategory
            selectedCategory?.let {
                categoryButton.text = it.descriptionProperty.get()
                categoryButton.style = """-fx-background-color: rgba(
                                           ${(it.colorProperty.get().red * 255).toInt()},
                                           ${(it.colorProperty.get().green * 255).toInt()},
                                           ${(it.colorProperty.get().blue * 255).toInt()},
                                           0.4
                                       );""".trimMargin()
            }
        }
        editingItem?.let {
            categoryButton.text = it.categoryProperty.get().descriptionProperty.get()
            categoryButton.style = """-fx-background-color: rgba(
                                           ${(it.categoryProperty.get().colorProperty.get().red * 255).toInt()},
                                           ${(it.categoryProperty.get().colorProperty.get().green * 255).toInt()},
                                           ${(it.categoryProperty.get().colorProperty.get().blue * 255).toInt()},
                                           0.4
                                       );""".trimMargin()
            selectedCategory = it.categoryProperty.get()
        }
    }
}