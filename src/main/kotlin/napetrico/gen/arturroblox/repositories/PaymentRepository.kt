package napetrico.gen.arturroblox.repositories

import napetrico.gen.arturroblox.entities.Item
import napetrico.gen.arturroblox.entities.Payment
import napetrico.gen.arturroblox.models.NewPayment
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class PaymentRepository {
    fun getAll() : List<Payment> = transaction {
        Payment.all().toList()
    }

    fun findById(id: Int): Payment? = transaction {
        Payment.findById(id)
    }

    fun create(payment: NewPayment) : Payment = transaction {
        Payment.new {
            total = payment.total
            paymentDate = payment.paymentDate
            relatedCart = payment.relatedCart
        }
    }
}