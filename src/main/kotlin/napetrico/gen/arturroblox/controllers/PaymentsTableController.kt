package napetrico.gen.arturroblox.controllers

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import napetrico.gen.arturroblox.entities.Payment
import napetrico.gen.arturroblox.models.CartItem
import java.math.BigDecimal
import java.net.URL
import java.time.LocalDate
import java.util.ResourceBundle

class PaymentsTableController: Initializable {
    @FXML private lateinit var paymentsTable: TableView<Payment>
    @FXML private lateinit var paymentTotal: TableColumn<Payment, BigDecimal>
    @FXML private lateinit var date: TableColumn<Payment, LocalDate>

    @FXML private lateinit var paymentDetailsTable: TableView<CartItem>
    @FXML private lateinit var quantity: TableColumn<CartItem, Int>
    @FXML private lateinit var item: TableColumn<CartItem, String>
    @FXML private lateinit var itemTotal: TableColumn<CartItem, BigDecimal>

    override fun initialize(url: URL?, rb: ResourceBundle?) {
        paymentsTable.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS
        paymentsTable.widthProperty().addListener { _, _, newWidth ->
            val w = newWidth.toDouble()

            paymentTotal.maxWidth = w * 0.40
            date.maxWidth = w * 0.60
        }

        paymentDetailsTable.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS
        paymentDetailsTable.widthProperty().addListener { _, _, newWidth ->
            val w = newWidth.toDouble()

            quantity.maxWidth = w * 0.25
            item.maxWidth = w * 0.50
            itemTotal.maxWidth = w * 0.25
        }
    }
}