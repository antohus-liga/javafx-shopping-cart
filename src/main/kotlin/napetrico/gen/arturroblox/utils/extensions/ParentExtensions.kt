package napetrico.gen.arturroblox.utils.extensions

import javafx.scene.Parent
import javafx.scene.Scene

fun Parent.toStyledScene(): Scene {
    return Scene(this).apply {
        stylesheets.add(
            javaClass.getResource("/napetrico/gen/arturroblox/styles/styles.css")!!.toExternalForm()
        )
    }
}
