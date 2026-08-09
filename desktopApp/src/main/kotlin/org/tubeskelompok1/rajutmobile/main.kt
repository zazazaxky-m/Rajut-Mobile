package org.tubeskelompok1.rajutmobile

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.tubeskelompok1.rajutmobile.di.initKoin

fun main() {
    initKoin()
    application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "rajut-mobile",
    ) {
        App()
    }
    }
}
