package napetrico.gen.arturroblox.controllers

import javafx.beans.binding.Bindings
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.util.Callback
import napetrico.gen.arturroblox.models.CartItem
import napetrico.gen.arturroblox.utils.assets.Assets
import napetrico.gen.arturroblox.utils.assets.ButtonTableCell
import napetrico.gen.arturroblox.utils.extensions.toStyledScene
import napetrico.gen.arturroblox.viewmodels.CartModel
import java.math.BigDecimal
import java.net.URL
import java.util.ResourceBundle

class ShoppingCartController: Initializable {
    @FXML private lateinit var cart: TableView<CartItem>
    @FXML private lateinit var add: TableColumn<CartItem, Void>
    @FXML private lateinit var sub: TableColumn<CartItem, Void>
    @FXML private lateinit var quantity: TableColumn<CartItem, Int>
    @FXML private lateinit var item: TableColumn<CartItem, String>
    @FXML private lateinit var total: TableColumn<CartItem, BigDecimal>
    @FXML private lateinit var remove: TableColumn<CartItem, Void>

    @FXML private lateinit var purchase: Button
    @FXML private lateinit var clear: Button

    lateinit var cartModel: CartModel

    // Anything data-related is supposed to go in this function, since it's where the data comes from
    fun initData(model: CartModel) {
        this.cartModel = model
        cart.items = cartModel.items

        purchase.disableProperty().bind(Bindings.isEmpty(cartModel.items))
    }

    override fun initialize(url: URL?, rb: ResourceBundle?) {
        cart.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS

        val tableButtons = arrayOf(add, sub, remove)

        cart.widthProperty().addListener { _, _, newWidth ->
            val buttonColumnWidth = 45.0
            // Width = table width - tableButtons size
            val w = (newWidth.toDouble() - buttonColumnWidth * tableButtons.size)

            // Buttons have fixed size because they can't get neither bigger nor smaller
            tableButtons.forEach { it.maxWidth = buttonColumnWidth }
            quantity.maxWidth = w * 0.20
            item.maxWidth = w * 0.60
            total.maxWidth = w * 0.20
        }

        quantity.cellValueFactory = Callback { it.value.quantityProperty.asObject() }
        item.cellValueFactory = Callback { it.value.descriptionProperty }
        total.cellValueFactory = Callback { it.value.totalPriceProperty }
        add.cellFactory = Callback {
            ButtonTableCell<CartItem>(Assets.PLUS_ICON) { row, e ->
                val delta = when {
                    e.isShiftDown -> 10
                    e.isControlDown -> 5
                    else -> 1
                }
                cartModel.changeQuantity(row, delta)
            }
        }
        sub.cellFactory = Callback {
            ButtonTableCell<CartItem>(Assets.MINUS_ICON) { row, e ->
                val delta = when {
                    e.isShiftDown -> -10
                    e.isControlDown -> -5
                    else -> -1
                }
                cartModel.changeQuantity(row, delta)
            }
        }
        remove.cellFactory = Callback {
            ButtonTableCell<CartItem>(Assets.TRASH_ICON) { row, _ ->
                cartModel.removeItem(row)
            }
        }
    }

    @FXML
    fun onPurchaseClick() {
        val loader = FXMLLoader(
            javaClass.getResource("/napetrico/gen/arturroblox/forms/payment-screen.fxml")
        )
        val root = loader.load<Parent>()

        val stage = Stage().apply {
            scene = root.toStyledScene()
            initModality(Modality.APPLICATION_MODAL)
            initOwner(purchase.scene.window)
            title = "Payment"
        }
        stage.show()
    }

    @FXML
    fun onClearClick() {
        Alert(Alert.AlertType.WARNING).apply {
            title = "Clear Cart"
            headerText = "Are you sure you want to clear this cart?"
            contentText = "If you confirm this action, the cart will be emptied."
            dialogPane.stylesheets.add(
                javaClass.getResource("/napetrico/gen/arturroblox/styles/styles.css")!!.toExternalForm()
            )
            showAndWait().ifPresent { response ->
                if (response == ButtonType.OK) {
                    cartModel.emptyShoppingCart()
                }
            }
        }
    }
}