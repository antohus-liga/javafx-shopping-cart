package napetrico.gen.arturroblox.controllers

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.stage.Stage
import javafx.util.Callback
import napetrico.gen.arturroblox.entities.ShoppingCart
import napetrico.gen.arturroblox.models.CartItemModel
import napetrico.gen.arturroblox.models.NewPayment
import napetrico.gen.arturroblox.viewmodels.CartViewModel
import napetrico.gen.arturroblox.viewmodels.PaymentViewModel
import java.math.BigDecimal
import java.net.URL
import java.util.ResourceBundle

class PaymentScreenController: Initializable {
    @FXML private lateinit var totalLabel: Label
    @FXML private lateinit var confirm: Button
    @FXML private lateinit var cancel: Button
    @FXML private lateinit var paymentMethod: ComboBox<String>

    @FXML private lateinit var shoppingCartTable: TableView<CartItemModel>
    @FXML private lateinit var item: TableColumn<CartItemModel, String>
    @FXML private lateinit var quantity: TableColumn<CartItemModel, Int>
    @FXML private lateinit var total: TableColumn<CartItemModel, String>

    private lateinit var cartModel: CartViewModel
    private lateinit var paymentModel: PaymentViewModel

    private lateinit var relatedCart: ShoppingCart
    private lateinit var cartTotal: BigDecimal

    private val paymentMethods = FXCollections.observableArrayList(
        "MB WAY",
        "Multibanco",
        "Credit / Debit card",
        "PayPal",
        "Bank Transfer (SEPA)",
    )

    fun initData(cartModel: CartViewModel, paymentModel: PaymentViewModel) {
        this.cartModel = cartModel
        this.paymentModel = paymentModel
        shoppingCartTable.items = cartModel.items

        relatedCart = cartModel.currentCart
        cartTotal = cartModel.getCartTotal()

        totalLabel.text = "${cartTotal.toString().replace(".", ",")} €"
    }

    override fun initialize(url: URL?, rb: ResourceBundle?) {
        shoppingCartTable.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS
        shoppingCartTable.widthProperty().addListener { _,  _, newWidth ->
            val w = newWidth.toDouble()

            item.maxWidth = w * 0.5
            quantity.maxWidth = w * 0.25
            total.maxWidth = w * 0.25
        }

        item.cellValueFactory = Callback { it.value.descriptionProperty }
        quantity.cellValueFactory = Callback { it.value.quantityProperty.asObject() }
        total.cellValueFactory = Callback {
            SimpleStringProperty(
                "${it.value.totalPriceProperty.get()} €".replace(".", ",")
            )
        }

        paymentMethod.value = "Select payment method"
        paymentMethod.items = paymentMethods
    }

    @FXML
    fun onCancelClick(actionEvent: ActionEvent) {
        val stage = (actionEvent.source as Button).scene.window as Stage
        stage.close()
    }

    @FXML
    fun onConfirmClick(actionEvent: ActionEvent) {
        if (!paymentMethods.contains(paymentMethod.value)) return

        paymentModel.add(NewPayment(paymentMethod.value, cartTotal, relatedCart.id.value))
        cartModel.createNewShoppingCart()

        val stage = (actionEvent.source as Button).scene.window as Stage
        stage.close()
    }
}