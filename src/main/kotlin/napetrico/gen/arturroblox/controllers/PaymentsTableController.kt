package napetrico.gen.arturroblox.controllers

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.util.Callback
import napetrico.gen.arturroblox.models.CartItemModel
import napetrico.gen.arturroblox.models.PaymentModel
import napetrico.gen.arturroblox.viewmodels.PaymentViewModel
import java.net.URL
import java.time.format.DateTimeFormatter
import java.util.ResourceBundle

class PaymentsTableController: Initializable {
    @FXML private lateinit var paymentsTable: TableView<PaymentModel>
    @FXML private lateinit var paymentTotal: TableColumn<PaymentModel, String>
    @FXML private lateinit var paymentMethod: TableColumn<PaymentModel, String>
    @FXML private lateinit var date: TableColumn<PaymentModel, String>

    @FXML private lateinit var paymentDetailsTable: TableView<CartItemModel>
    @FXML private lateinit var quantity: TableColumn<CartItemModel, Int>
    @FXML private lateinit var item: TableColumn<CartItemModel, String>
    @FXML private lateinit var itemTotal: TableColumn<CartItemModel, String>

    @FXML private lateinit var parentBox: HBox
    @FXML private lateinit var leftBox: VBox
    @FXML private lateinit var rightBox: VBox

    private lateinit var paymentViewModel: PaymentViewModel

    fun initData(paymentViewModel: PaymentViewModel) {
        this.paymentViewModel = paymentViewModel
        paymentsTable.items = paymentViewModel.payments
        paymentDetailsTable.items = paymentViewModel.currentPaymentDetail
    }

    override fun initialize(url: URL?, rb: ResourceBundle?) {
        leftBox.prefWidthProperty().bind(parentBox.widthProperty().multiply(0.53))
        rightBox.prefWidthProperty().bind(parentBox.widthProperty().multiply(0.47))

        paymentsTable.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS
        paymentsTable.widthProperty().addListener { _, _, newWidth ->
            val w = newWidth.toDouble()

            paymentTotal.maxWidth = w * 0.23
            date.maxWidth = w * 0.23
            paymentMethod.maxWidth = w * 0.54
        }

        paymentMethod.cellValueFactory = Callback { SimpleStringProperty(it.value.paymentMethod) }
        paymentTotal.cellValueFactory = Callback {
            SimpleStringProperty(
                "${paymentViewModel.getPaymentTotal(it.value)} €"
                    .replace(".", ",")
            )
        }
        date.cellValueFactory = Callback {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

            SimpleObjectProperty(it.value.paymentDate.format(formatter))
        }

        paymentDetailsTable.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS
        paymentDetailsTable.widthProperty().addListener { _, _, newWidth ->
            val w = newWidth.toDouble()

            quantity.maxWidth = w * 0.25
            item.maxWidth = w * 0.55
            itemTotal.maxWidth = w * 0.20
        }

        quantity.cellValueFactory = Callback { it.value.quantityProperty.asObject() }
        item.cellValueFactory = Callback { it.value.descriptionProperty }
        itemTotal.cellValueFactory = Callback {
            SimpleStringProperty(
                "${it.value.totalPriceProperty.get()} €"
                    .replace(".", ",")
            )
        }

        paymentsTable.selectionModel.selectedItemProperty().addListener { _, _, payment ->
            paymentViewModel.changeCurrentPayment(payment)
        }

        paymentDetailsTable.placeholder = Label("Clique num pagamento para ver os detalhes.").apply {
            style = "-fx-text-fill: gray; -fx-font-size: 14;"
        }
    }
}