package napetrico.gen.arturroblox.controllers

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.layout.AnchorPane
import napetrico.gen.arturroblox.viewmodels.CartViewModel
import napetrico.gen.arturroblox.viewmodels.ItemViewModel
import napetrico.gen.arturroblox.viewmodels.PaymentViewModel
import java.net.URL
import java.util.ResourceBundle

class ItemShoppingController {
    @FXML lateinit var shoppingCartController: ShoppingCartController
    @FXML lateinit var itemListController: ItemListController

    private val cartViewModel = CartViewModel()
    private val itemViewModel = ItemViewModel()
    private lateinit var paymentViewModel: PaymentViewModel

    fun initData(paymentViewModel: PaymentViewModel) {
        this.paymentViewModel = paymentViewModel

        shoppingCartController.initData(cartViewModel, paymentViewModel)
        itemListController.initData(cartViewModel, itemViewModel)
    }
}