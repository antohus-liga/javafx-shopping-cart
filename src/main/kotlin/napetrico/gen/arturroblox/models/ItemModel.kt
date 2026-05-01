package napetrico.gen.arturroblox.models

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import java.math.BigDecimal

class ItemModel (
    val id: Int,
    description: String,
    unitPrice: BigDecimal
) {
    val descriptionProperty = SimpleStringProperty(description)
    val unitPriceProperty = SimpleObjectProperty(unitPrice)
}