package napetrico.gen.arturroblox.models

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.paint.Color
import java.math.BigDecimal

data class NewCategory (
    val id: Int,
    val description: String,
    val color: Color,
)