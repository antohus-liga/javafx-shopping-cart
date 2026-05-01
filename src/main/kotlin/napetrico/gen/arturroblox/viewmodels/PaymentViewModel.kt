package napetrico.gen.arturroblox.viewmodels

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import napetrico.gen.arturroblox.models.NewPayment
import napetrico.gen.arturroblox.models.PaymentModel
import napetrico.gen.arturroblox.repositories.CartItemRepository
import napetrico.gen.arturroblox.repositories.PaymentRepository
import java.math.BigDecimal

class PaymentViewModel {
    private val paymentRepository = PaymentRepository()
    private val cartItemRepository = CartItemRepository()

    val payments: ObservableList<PaymentModel> = FXCollections.observableArrayList()

    init {
        payments.addAll(paymentRepository.getAll())
    }

    fun add(newPayment: NewPayment) {
        val id = paymentRepository.create(newPayment)
        payments.add(PaymentModel(id.value, newPayment.paymentMethod, newPayment.paymentDate, newPayment.relatedCart))
    }

    fun getPaymentTotal(payment: PaymentModel): BigDecimal {
        return cartItemRepository.getItemsByCart(
            payment.relatedCart
        ).sumOf { it.totalPriceProperty.value }
    }
}