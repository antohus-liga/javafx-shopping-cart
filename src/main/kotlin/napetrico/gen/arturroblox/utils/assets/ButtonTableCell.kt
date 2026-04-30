package napetrico.gen.arturroblox.utils.assets

import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.TableCell
import javafx.scene.shape.SVGPath

class ButtonTableCell<T>(
    svg: String,
    private val onClick: (T) -> Unit
) : TableCell<T, Void>() {
    val path = SVGPath().apply {
        content = svg
    }

    private val button = Button().apply {
        style = "-fx-font-size: 12; -fx-alignment: center;"
        graphic = path
        onAction = EventHandler {
            tableRow.item?.let(onClick)
            tableView.refresh()
        }
    }

    override fun updateItem(item: Void?, empty: Boolean) {
        super.updateItem(item, empty)
        graphic = if (empty) null else button
    }
}