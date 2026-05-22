package napetrico.gen.arturroblox.models

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.paint.Color
import java.math.BigDecimal

class CategoryModel (
    val id: Int,
    description: String,
    color: Color,
) {
    val descriptionProperty = SimpleStringProperty(description)
    val colorProperty = SimpleObjectProperty(color)
}