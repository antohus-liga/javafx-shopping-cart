package napetrico.gen.arturroblox

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.stage.Stage
import napetrico.gen.arturroblox.utils.extensions.toStyledScene

class ShoppingCartApplication : Application() {
    override fun start(stage: Stage) {
        val root = FXMLLoader(
            ShoppingCartApplication::class.java.getResource("main.fxml")
        ).load<Parent>()

        stage.scene = root.toStyledScene()

        stage.title = "Shopping Cart"
        stage.show()
    }
}
