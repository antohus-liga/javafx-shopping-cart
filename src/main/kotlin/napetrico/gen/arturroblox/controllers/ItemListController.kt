package napetrico.gen.arturroblox.controllers

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.stage.Modality
import javafx.stage.Stage
import napetrico.gen.arturroblox.entities.Item
import napetrico.gen.arturroblox.utils.extensions.toStyledScene
import java.math.BigDecimal
import java.net.URL
import java.util.ResourceBundle

class ItemListController: Initializable {
    @FXML private lateinit var itemsTable: TableView<Item>
    @FXML private lateinit var description: TableColumn<Item, String>
    @FXML private lateinit var unitPrice: TableColumn<Item, BigDecimal>
    @FXML private lateinit var add: TableColumn<Item, Button>

    @FXML private lateinit var createItem: Button

    override fun initialize(url: URL?, rb: ResourceBundle?) {
        itemsTable.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS

        itemsTable.widthProperty().addListener { _, _, newWidth ->
            val w = newWidth.toDouble()

            description.maxWidth = w * 0.60
            unitPrice.maxWidth = w * 0.20
            add.maxWidth = w * 0.20
        }
    }

    @FXML
    fun onCreateItemClick() {
        val loader = FXMLLoader(javaClass.getResource("/napetrico/gen/arturroblox/forms/add-item.fxml"))
        val root = loader.load<Parent>()

        val stage = Stage().apply {
            scene = root.toStyledScene()
            initModality(Modality.APPLICATION_MODAL)
            initOwner(createItem.scene.window)
            title = "Create Item"
        }
        stage.show()
    }
}