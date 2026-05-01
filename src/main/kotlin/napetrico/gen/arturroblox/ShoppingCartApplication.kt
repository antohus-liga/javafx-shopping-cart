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
        // This size is hardcoded to be just enough to show everything that is needed to be seen
        stage.width = 750.0
        stage.minWidth = 750.0

        stage.title = "Shopping Cart"
        stage.show()
    }
}
