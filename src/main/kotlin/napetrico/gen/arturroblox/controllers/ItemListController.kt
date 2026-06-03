package napetrico.gen.arturroblox.controllers

import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableRow
import javafx.scene.control.TableView
import javafx.scene.input.MouseButton
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.util.Callback
import napetrico.gen.arturroblox.models.CategoryModel
import napetrico.gen.arturroblox.models.ItemModel
import napetrico.gen.arturroblox.utils.assets.Assets
import napetrico.gen.arturroblox.utils.assets.ButtonTableCell
import napetrico.gen.arturroblox.utils.extensions.toStyledScene
import napetrico.gen.arturroblox.viewmodels.CartViewModel
import napetrico.gen.arturroblox.viewmodels.CategoryViewModel
import napetrico.gen.arturroblox.viewmodels.ItemViewModel
import java.math.BigDecimal
import java.net.URL
import java.util.ResourceBundle

class ItemListController: Initializable {
    @FXML private lateinit var itemsTable: TableView<ItemModel>
    @FXML private lateinit var description: TableColumn<ItemModel, String>
    @FXML private lateinit var unitPrice: TableColumn<ItemModel, String>
    @FXML private lateinit var add: TableColumn<ItemModel, Void>
    @FXML private lateinit var remove: TableColumn<ItemModel, Void>

    @FXML private lateinit var createItem: Button

    private lateinit var cartViewModel: CartViewModel
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var categoryViewModel: CategoryViewModel

    fun initData(cartViewModel: CartViewModel, itemViewModel: ItemViewModel, categoryViewModel: CategoryViewModel) {
        this.cartViewModel = cartViewModel
        this.itemViewModel = itemViewModel
        this.categoryViewModel = categoryViewModel

        itemsTable.items = itemViewModel.items
    }

    override fun initialize(url: URL?, rb: ResourceBundle?) {
        itemsTable.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS

        itemsTable.widthProperty().addListener { _, _, newWidth ->
            val w = newWidth.toDouble()

            description.maxWidth = w * 0.60
            unitPrice.maxWidth = w * 0.20
            add.maxWidth = w * 0.20
        }
        val tableButtons = arrayOf(add, remove)

        itemsTable.widthProperty().addListener { _, _, newWidth ->
            val buttonColumnWidth = 45.0
            // Width = table width - tableButtons size
            val w = (newWidth.toDouble() - buttonColumnWidth * tableButtons.size)

            // Buttons have fixed size because they can't get neither bigger nor smaller
            tableButtons.forEach { it.maxWidth = buttonColumnWidth }
            description.maxWidth = w * 0.70
            unitPrice.maxWidth = w * 0.30
        }

        itemsTable.rowFactory = Callback {
            object : TableRow<ItemModel>() {
                init {
                    onMouseClicked = EventHandler { event ->
                        if (event.button == MouseButton.SECONDARY && !this.isEmpty) {
                            openEditForm(this.item)
                        }
                    }
                }

                override fun updateItem(item: ItemModel?, empty: Boolean) {
                    super.updateItem(item, empty)

                    if (empty || item == null) {
                        style = ""
                        return
                    }

                    val color = item.categoryProperty.get().colorProperty.get()

                    if (color != null) {
                        style = """-fx-background-color: rgba(
                            ${(color.red * 255).toInt()}, 
                            ${(color.green * 255).toInt()}, 
                            ${(color.blue * 255).toInt()},
                            0.4
                        );"""
                    }
                }
            }
        }

        description.cellValueFactory = Callback { it.value.descriptionProperty }
        unitPrice.cellValueFactory = Callback {
            Bindings.createStringBinding(
                {
                    "${it.value.unitPriceProperty.get()} €".replace(".", ",")
                },
                it.value.unitPriceProperty
            )
        }
        add.cellFactory = Callback {
            ButtonTableCell<ItemModel>(Assets.CART_ICON) { row, _ ->
                cartViewModel.addItem(row)
            }
        }
        remove.cellFactory = Callback {
            ButtonTableCell<ItemModel>(Assets.TRASH_ICON) { row, _ ->
                itemViewModel.remove(row)
                cartViewModel.removeItem(row)
            }
        }
    }

    private fun openEditForm(item: ItemModel) {
        val loader = FXMLLoader(javaClass.getResource("/napetrico/gen/arturroblox/forms/item-form.fxml"))
        val root = loader.load<Parent>()

        val controller = loader.getController<ItemFormController>()
        controller.initEdit(itemViewModel, cartViewModel, categoryViewModel, item)

        Stage().apply {
            scene = root.toStyledScene()
            initModality(Modality.APPLICATION_MODAL)
            title = "Editar artigo ${item.descriptionProperty.get()}"
            show()
        }

        itemsTable.refresh()
    }

    @FXML
    fun onCreateItemClick() {
        val loader = FXMLLoader(javaClass.getResource("/napetrico/gen/arturroblox/forms/item-form.fxml"))
        val root = loader.load<Parent>()

        val controller = loader.getController<ItemFormController>()
        controller.initCreate(itemViewModel, categoryViewModel)

        val stage = Stage().apply {
            scene = root.toStyledScene()
            initModality(Modality.APPLICATION_MODAL)
            initOwner(createItem.scene.window)
            title = "Criar artigo"
        }
        stage.show()

        itemsTable.refresh()
    }
}