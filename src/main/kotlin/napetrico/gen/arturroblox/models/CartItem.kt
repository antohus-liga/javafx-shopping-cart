package napetrico.gen.arturroblox.models

import javafx.beans.property.SimpleIntegerProperty
import java.math.BigDecimal

data class CartItem(
    var itemId: Int,
    var description: String,
    var quantity: SimpleIntegerProperty,
    var unitPrice: BigDecimal
) {
    val totalPrice: BigDecimal
        get() = unitPrice * quantity.get().toBigDecimal()
}
