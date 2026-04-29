package napetrico.gen.arturroblox.controllers

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.stage.Stage
import napetrico.gen.arturroblox.entities.Item
import java.math.BigDecimal
import java.net.URL
import java.util.ResourceBundle

class PaymentScreenController: Initializable {
    @FXML private lateinit var totalLabel: Label
    @FXML private lateinit var confirm: Button
    @FXML private lateinit var cancel: Button
    @FXML private lateinit var paymentMethod: ComboBox<String>

    @FXML private lateinit var shoppingCartTable: TableView<Item>
    @FXML private lateinit var item: TableColumn<Item, String>
    @FXML private lateinit var quantity: TableColumn<Item, Int>
    @FXML private lateinit var total: TableColumn<Item, BigDecimal>

    override fun initialize(url: URL?, rb: ResourceBundle?) {
        shoppingCartTable.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS
        shoppingCartTable.widthProperty().addListener { _,  _, newWidth ->
            val w = newWidth.toDouble()

            quantity.maxWidth = w * 0.25
            item.maxWidth = w * 0.5
            total.maxWidth = w * 0.25
        }

        val paymentMethods = FXCollections.observableArrayList(
            "MB WAY",
            "Multibanco",
            "Credit / Debit card",
            "PayPal",
            "Bank Transfer (SEPA)",
        )
        paymentMethod.value = "Select payment method"
        paymentMethod.items = paymentMethods
    }

    @FXML
    fun onCancelClick(actionEvent: ActionEvent) {
        val stage = (actionEvent.source as Button).scene.window as Stage
        stage.close()
    }
}