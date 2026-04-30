package napetrico.gen.arturroblox.models

import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import java.math.BigDecimal

data class CartItem(
    val itemId: Int,
    val description: String,
    val quantity: Int,
    val unitPrice: BigDecimal
) {
    val descriptionProperty = SimpleStringProperty(description)
    val quantityProperty = SimpleIntegerProperty(quantity)
    val unitPriceProperty = SimpleObjectProperty(unitPrice)

    val totalPriceProperty = SimpleObjectProperty<BigDecimal>()

    init {
        totalPriceProperty.bind(
            Bindings.createObjectBinding(
                {
                    unitPriceProperty.get().multiply(quantityProperty.get().toBigDecimal())
                },
                quantityProperty, unitPriceProperty
            )
        )
    }
}
