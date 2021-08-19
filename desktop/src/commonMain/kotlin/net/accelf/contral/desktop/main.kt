package net.accelf.contral.desktop

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import net.accelf.contral.core.Main

@ExperimentalComposeUiApi
@ExperimentalDecomposeApi
fun main() {
    val lifecycle = LifecycleRegistry()

    application {
        val windowState = rememberWindowState()

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
        ) {
            Main(ctx = DefaultComponentContext(lifecycle))
        }
    }
}
