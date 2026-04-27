package napetrico.gen.arturroblox.models

import javafx.beans.property.SimpleIntegerProperty
import java.math.BigDecimal

data class CartItem(
    var itemId: Int,
    var description: String,
    var quantity: SimpleIntegerProperty,
    var unitPrice: BigDecimal
) {
    var totalPrice: BigDecimal = unitPrice * quantity.get().toBigDecimal()
}
