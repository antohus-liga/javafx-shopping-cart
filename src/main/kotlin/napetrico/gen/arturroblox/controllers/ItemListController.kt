package napetrico.gen.arturroblox.controllers

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import napetrico.gen.arturroblox.entities.Item
import java.math.BigDecimal
import java.net.URL
import java.util.ResourceBundle

class ItemListController: Initializable {
    @FXML private lateinit var itemsTable: TableView<Item>
    @FXML private lateinit var description: TableColumn<Item, String>
    @FXML private lateinit var unitPrice: TableColumn<Item, BigDecimal>
    @FXML private lateinit var add: TableColumn<Item, Button>

    override fun initialize(url: URL?, rb: ResourceBundle?) {
        itemsTable.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS;
        description.prefWidthProperty().bind(itemsTable.widthProperty().multiply(0.60));
        unitPrice.prefWidthProperty().bind(itemsTable.widthProperty().multiply(0.2));
        add.prefWidthProperty().bind(itemsTable.widthProperty().multiply(0.20));
    }
}