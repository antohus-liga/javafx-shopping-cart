package napetrico.gen.arturroblox.controllers

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.util.Callback
import napetrico.gen.arturroblox.models.CartItem
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

    @FXML private lateinit var purchase: Button
    @FXML private lateinit var clear: Button

    lateinit var cartModel: CartModel

    fun init(model: CartModel) {
        this.cartModel = model
        cart.items = cartModel.items
    }

    override fun initialize(url: URL?, rb: ResourceBundle?) {
        cart.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS

        cart.widthProperty().addListener { _, _, newWidth ->
            val w = newWidth.toDouble()
            add.maxWidth = w * 0.05
            sub.maxWidth = w * 0.05
            quantity.maxWidth = w * 0.20
            item.maxWidth = w * 0.50
            total.maxWidth = w * 0.20
        }

        quantity.cellValueFactory = Callback { it.value.quantity.asObject() }
        item.cellValueFactory = Callback { SimpleStringProperty(it.value.description) }
        total.cellValueFactory = Callback { SimpleObjectProperty(it.value.totalPrice) }
        add.cellFactory = Callback {
            object : TableCell<CartItem, Void>() {
                private val button = Button("+")

                init {
                    alignment = Pos.CENTER

                    button.onAction = EventHandler {
                        val row = tableRow.item ?: return@EventHandler
                        row.quantity.set(row.quantity.get() + 1)
                        tableView.refresh()
                    }
                }

                override fun updateItem(item: Void?, empty: Boolean) {
                    super.updateItem(item, empty)
                    graphic = if (empty) null else button
                }
            }
        }
        sub.cellFactory = Callback {
            object : TableCell<CartItem, Void>() {
                private val button = Button("-")

                init {
                    alignment = Pos.CENTER

                    button.onAction = EventHandler {
                        val row = tableRow.item ?: return@EventHandler
                        row.quantity.set(row.quantity.get() - 1)
                        tableView.refresh()
                    }
                }

                override fun updateItem(item: Void?, empty: Boolean) {
                    super.updateItem(item, empty)
                    graphic = if (empty) null else button
                }
            }
        }
    }

    @FXML
    fun onPurchaseClick() {
        val loader = FXMLLoader(javaClass.getResource("/napetrico/gen/arturroblox/forms/payment-screen.fxml"))
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
        cart.items.clear()
    }
}