package net.accelf.contral.desktop

import net.accelf.contral.core.ui.Main
import androidx.compose.desktop.Window
import net.accelf.contral.desktop.config.DesktopConfigurationProvider

fun main() = Window {
    Main("Desktop", DesktopConfigurationProvider())
}
