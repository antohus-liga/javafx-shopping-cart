package napetrico.gen.arturroblox

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class ShoppingCartApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(ShoppingCartApplication::class.java.getResource("main.fxml"))
        val scene = Scene(fxmlLoader.load())
        stage.title = "Shopping Cart"
        stage.scene = scene
        stage.show()
    }
}
  
