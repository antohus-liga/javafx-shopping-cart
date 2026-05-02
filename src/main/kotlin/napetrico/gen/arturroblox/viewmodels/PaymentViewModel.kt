package napetrico.gen.arturroblox.viewmodels

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.ObservableMap
import napetrico.gen.arturroblox.models.CartItemModel
import napetrico.gen.arturroblox.models.NewPayment
import napetrico.gen.arturroblox.models.PaymentModel
import napetrico.gen.arturroblox.repositories.CartItemRepository
import napetrico.gen.arturroblox.repositories.PaymentRepository
import java.math.BigDecimal

class PaymentViewModel {
    private val paymentRepository = PaymentRepository()
    private val cartItemRepository = CartItemRepository()

    val paymentDetail: ObservableMap<Int, ObservableList<CartItemModel>> = FXCollections.observableHashMap()
    var currentPaymentDetail: ObservableList<CartItemModel> = FXCollections.observableArrayList()

    val payments: ObservableList<PaymentModel> = FXCollections.observableArrayList()

    init {
        payments.addAll(paymentRepository.getAll())
        for (p in payments) {
            paymentDetail[p.relatedCart] = FXCollections.observableArrayList(
                cartItemRepository.getItemsByCart(p.relatedCart)
            )
        }
    }

    fun add(newPayment: NewPayment) {
        val id = paymentRepository.create(newPayment)
        payments.add(PaymentModel(id.value, newPayment.paymentMethod, newPayment.paymentDate, newPayment.relatedCart))
        paymentDetail[newPayment.relatedCart] = FXCollections.observableArrayList(
            cartItemRepository.getItemsByCart(newPayment.relatedCart)
        )
    }

    fun getPaymentTotal(payment: PaymentModel): BigDecimal =
        cartItemRepository.getItemsByCart(
            payment.relatedCart
        ).sumOf { it.totalPriceProperty.value }

    fun changeCurrentPayment(payment: PaymentModel) {
        currentPaymentDetail.setAll(paymentDetail[payment.relatedCart] ?: return)
    }
}