package napetrico.gen.arturroblox.controllers

import javafx.fxml.FXML
import javafx.fxml.Initializable
import napetrico.gen.arturroblox.viewmodels.PaymentViewModel
import java.net.URL
import java.util.ResourceBundle

class MainController : Initializable {
    @FXML lateinit var itemShoppingController: ItemShoppingController
    @FXML lateinit var paymentsTableController: PaymentsTableController

    private val paymentViewModel: PaymentViewModel = PaymentViewModel()

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        itemShoppingController.initData(paymentViewModel)
        paymentsTableController.initData(paymentViewModel)
    }
}
