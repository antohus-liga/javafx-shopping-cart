package napetrico.gen.arturroblox.repositories

import napetrico.gen.arturroblox.dsl.Payments
import napetrico.gen.arturroblox.models.NewPayment
import napetrico.gen.arturroblox.models.PaymentModel
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class PaymentRepository {
    fun getAll() : List<PaymentModel> = transaction {
        Payments.selectAll().map { it.toDto() }
    }

    fun create(newPayment: NewPayment) = transaction {
        Payments.insert {
            it[paymentMethod] = newPayment.paymentMethod
            it[total] = newPayment.total
            it[paymentDate] = newPayment.paymentDate
            it[relatedCart] = newPayment.relatedCart
        } get Payments.id
    }

    fun ResultRow.toDto() = PaymentModel(
        this[Payments.id].value,
        this[Payments.paymentMethod],
        this[Payments.paymentDate],
        this[Payments.relatedCart].value
    )
}