package napetrico.gen.arturroblox

import javafx.application.Application
import napetrico.gen.arturroblox.dsl.CartItems
import napetrico.gen.arturroblox.dsl.Items
import napetrico.gen.arturroblox.dsl.Payments
import napetrico.gen.arturroblox.dsl.ShoppingCarts
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun main() {
    Database.connect(
        "jdbc:sqlite:shopping_history.db?foreign_keys=on", driver = "org.sqlite.JDBC"
    )

    transaction {
        SchemaUtils.create(CartItems, Items, Payments, ShoppingCarts)
    }

    Application.launch(ShoppingCartApplication::class.java)
}
