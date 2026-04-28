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
        paymentsTable.columnResizePolicy = TableView.UNCONSTRAINED_RESIZE_POLICY

        paymentTotal.prefWidthProperty().bind(paymentsTable.widthProperty().multiply(0.4))
        date.prefWidthProperty().bind(paymentsTable.widthProperty().multiply(0.6))

        paymentDetailsTable.columnResizePolicy = TableView.UNCONSTRAINED_RESIZE_POLICY

        quantity.prefWidthProperty().bind(paymentDetailsTable.widthProperty().multiply(0.25))
        item.prefWidthProperty().bind(paymentDetailsTable.widthProperty().multiply(0.5))
        itemTotal.prefWidthProperty().bind(paymentDetailsTable.widthProperty().multiply(0.25))
    }
}