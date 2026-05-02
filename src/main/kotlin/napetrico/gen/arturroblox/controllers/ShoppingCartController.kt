package napetrico.gen.arturroblox.controllers

import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleStringProperty
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.util.Callback
import napetrico.gen.arturroblox.models.CartItemModel
import napetrico.gen.arturroblox.utils.assets.Assets
import napetrico.gen.arturroblox.utils.assets.ButtonTableCell
import napetrico.gen.arturroblox.utils.extensions.toStyledScene
import napetrico.gen.arturroblox.viewmodels.CartViewModel
import napetrico.gen.arturroblox.viewmodels.PaymentViewModel
import java.math.BigDecimal
import java.net.URL
import java.util.ResourceBundle

class ShoppingCartController: Initializable {
    @FXML private lateinit var cart: TableView<CartItemModel>
    @FXML private lateinit var add: TableColumn<CartItemModel, Void>
    @FXML private lateinit var sub: TableColumn<CartItemModel, Void>
    @FXML private lateinit var quantity: TableColumn<CartItemModel, Int>
    @FXML private lateinit var item: TableColumn<CartItemModel, String>
    @FXML private lateinit var total: TableColumn<CartItemModel, String>
    @FXML private lateinit var remove: TableColumn<CartItemModel, Void>

    @FXML private lateinit var purchase: Button
    @FXML private lateinit var clear: Button

    private lateinit var cartViewModel: CartViewModel
    private lateinit var paymentViewModel: PaymentViewModel

    // Anything data-related is supposed to go in this function, since it's where the data comes from
    fun initData(model: CartViewModel, paymentViewModel: PaymentViewModel) {
        this.cartViewModel = model
        this.paymentViewModel = paymentViewModel
        cart.items = cartViewModel.items

        purchase.disableProperty().bind(Bindings.isEmpty(cartViewModel.items))
        clear.disableProperty().bind(Bindings.isEmpty(cartViewModel.items))
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
            quantity.maxWidth = w * 0.25
            item.maxWidth = w * 0.55
            total.maxWidth = w * 0.20
        }

        quantity.cellValueFactory = Callback { it.value.quantityProperty.asObject() }
        item.cellValueFactory = Callback { it.value.descriptionProperty }
        total.cellValueFactory = Callback {
            Bindings.createStringBinding(
                {
                    "${it.value.totalPriceProperty.get()} €".replace(".", ",")
                },
                it.value.totalPriceProperty
            )
        }
        add.cellFactory = Callback {
            ButtonTableCell<CartItemModel>(Assets.PLUS_ICON) { row, e ->
                val delta = when {
                    e.isShiftDown -> 10
                    e.isControlDown -> 5
                    else -> 1
                }
                cartViewModel.changeQuantity(row, delta)
            }
        }
        sub.cellFactory = Callback {
            ButtonTableCell<CartItemModel>(Assets.MINUS_ICON) { row, e ->
                val delta = when {
                    e.isShiftDown -> -10
                    e.isControlDown -> -5
                    else -> -1
                }
                cartViewModel.changeQuantity(row, delta)
            }
        }
        remove.cellFactory = Callback {
            ButtonTableCell<CartItemModel>(Assets.TRASH_ICON) { row, _ ->
                cartViewModel.removeItem(row)
            }
        }

        cart.placeholder = Label("Adicione um produto ao carrinho para conseguir efetuar uma compra.").apply {
            style = "-fx-text-fill: gray; -fx-font-size: 14;"
        }
    }

    @FXML
    fun onPurchaseClick() {
        val loader = FXMLLoader(
            javaClass.getResource("/napetrico/gen/arturroblox/forms/payment-screen.fxml")
        )
        val root = loader.load<Parent>()

        val controller = loader.getController<PaymentScreenController>()
        controller.initData(cartViewModel, paymentViewModel)

        val stage = Stage().apply {
            scene = root.toStyledScene()
            initModality(Modality.APPLICATION_MODAL)
            initOwner(purchase.scene.window)
            title = "Pagamento"
        }
        stage.show()
    }

    @FXML
    fun onClearClick() {
        Alert(Alert.AlertType.WARNING).apply {
            title = "Limpar o carrinho"
            headerText = "Tem a certeza que quer limpar o carrinho?"
            contentText = "Se confirmar, não conseguirá cancelar este processo."
            dialogPane.stylesheets.add(
                javaClass.getResource("/napetrico/gen/arturroblox/styles/styles.css")!!.toExternalForm()
            )
            showAndWait().ifPresent { response ->
                if (response == ButtonType.OK) {
                    cartViewModel.emptyShoppingCart()
                }
            }
        }
    }
}