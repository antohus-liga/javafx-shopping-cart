package napetrico.gen.arturroblox.models

import javafx.beans.property.SimpleIntegerProperty
import java.math.BigDecimal

data class UpdateCartItem(
    val itemId: Int?,
    val description: String?,
    val quantity: SimpleIntegerProperty?,
    val unitPrice: BigDecimal?
) {
    fun getTotalPrice(): BigDecimal? {
        if (unitPrice == null || quantity == null) return null

        return unitPrice * quantity.get().toBigDecimal()
    }
}
