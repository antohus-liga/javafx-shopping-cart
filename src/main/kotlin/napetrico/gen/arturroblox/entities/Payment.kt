package napetrico.gen.arturroblox.entities

import napetrico.gen.arturroblox.dsl.Payments
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import java.time.LocalDate

class Payment(id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Payment>(Payments)

    var total by Payments.total
    var paymentDate: LocalDate by Payments.paymentDate
    var relatedCart by ShoppingCart referencedOn Payments.relatedCart
}