package napetrico.gen.arturroblox.controllers

import javafx.beans.property.SimpleIntegerProperty
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.stage.Modality
import javafx.stage.Stage
import napetrico.gen.arturroblox.models.CartItem
import java.math.BigDecimal
import java.net.URL
import java.util.ResourceBundle

class ShoppingCartController: Initializable {
    @FXML private lateinit var cart: TableView<CartItem>
    @FXML private lateinit var add: TableColumn<CartItem, Button>
    @FXML private lateinit var sub: TableColumn<CartItem, Button>
    @FXML private lateinit var quantity: TableColumn<CartItem, Int>
    @FXML private lateinit var item: TableColumn<CartItem, String>
    @FXML private lateinit var total: TableColumn<CartItem, BigDecimal>

    @FXML private lateinit var purchase: Button
    @FXML private lateinit var clear: Button

    override fun initialize(url: URL?, rb: ResourceBundle?) {
        add.prefWidthProperty().bind(cart.widthProperty().multiply(0.05))
        sub.prefWidthProperty().bind(cart.widthProperty().multiply(0.05))
        quantity.prefWidthProperty().bind(cart.widthProperty().multiply(0.20))
        item.prefWidthProperty().bind(cart.widthProperty().multiply(0.50))
        total.prefWidthProperty().bind(cart.widthProperty().multiply(0.20))
    }

    @FXML
    fun onPurchaseClick() {
        val loader = FXMLLoader(javaClass.getResource("/napetrico/gen/arturroblox/forms/payment-screen.fxml"))
        val stage = Stage().apply {
            scene = Scene(loader.load())
            initModality(Modality.APPLICATION_MODAL)
            initOwner(purchase.scene.window)
            title = "Payment"
        }
        stage.show()
    }

    @FXML
    fun onClearClick() {
        cart.items.clear()
    }
}