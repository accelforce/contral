package net.accelf.contral.core

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import net.accelf.contral.core.config.ConfigurationProvider
import net.accelf.contral.core.config.LocalConfiguration
import net.accelf.contral.core.router.LocalRouter
import net.accelf.contral.core.router.Route
import net.accelf.contral.core.router.Router

@Composable
fun Main(
    ctx: ComponentContext,
    configurationProvider: ConfigurationProvider,
) {
    val router = Router(ctx, Route(""), builtinRoutes)
    CompositionLocalProvider(LocalConfiguration provides configurationProvider) {
        CompositionLocalProvider(LocalRouter provides router.instance) {
            MaterialTheme {
                Children(router.state) {
                    it.instance()
                }
            }
        }
    }
}
