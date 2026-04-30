package napetrico.gen.arturroblox.controllers

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.layout.AnchorPane
import napetrico.gen.arturroblox.viewmodels.CartModel
import java.net.URL
import java.util.ResourceBundle

class ItemShoppingController : Initializable {
    @FXML lateinit var shoppingCart: AnchorPane
    @FXML lateinit var shoppingCartController: ShoppingCartController

    private val cartModel = CartModel()

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        shoppingCartController.initData(cartModel)
    }
}