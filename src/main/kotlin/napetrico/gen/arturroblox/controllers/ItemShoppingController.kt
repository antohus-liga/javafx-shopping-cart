package napetrico.gen.arturroblox.controllers

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.layout.AnchorPane
import napetrico.gen.arturroblox.viewmodels.CartViewModel
import napetrico.gen.arturroblox.viewmodels.ItemViewModel
import java.net.URL
import java.util.ResourceBundle

class ItemShoppingController : Initializable {
    @FXML lateinit var shoppingCartController: ShoppingCartController
    @FXML lateinit var itemListController: ItemListController

    private val cartViewModel = CartViewModel()
    private val itemViewModel = ItemViewModel()

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        shoppingCartController.initData(cartViewModel)
        itemListController.initData(cartViewModel, itemViewModel)
    }
}