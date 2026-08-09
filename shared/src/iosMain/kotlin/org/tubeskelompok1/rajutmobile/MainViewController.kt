package org.tubeskelompok1.rajutmobile

import androidx.compose.ui.window.ComposeUIViewController
import org.tubeskelompok1.rajutmobile.di.initKoin

private val koinApplication by lazy { initKoin() }

fun MainViewController() = ComposeUIViewController {
    koinApplication
    App()
}
