package net.accelf.contral.core

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import net.accelf.contral.core.config.LocalConfiguration
import net.accelf.contral.core.config.configurationProvider
import net.accelf.contral.core.plugin.LocalPlugins
import net.accelf.contral.core.plugin.resolvePlugins
import net.accelf.contral.core.router.LocalRouter
import net.accelf.contral.core.router.Route
import net.accelf.contral.core.router.Router
import net.accelf.contral.core.timelines.LocalTimelineHandlers
import net.accelf.contral.core.timelines.TimelineHandlersBuilder

@Composable
fun Main(
    ctx: ComponentContext,
) {
    val plugins = resolvePlugins()
    val routes = builtinRoutes.apply {
        plugins.map { it.routes }.forEach { plusAssign(it) }
    }.build()
    val router = Router(ctx, Route("home"), routes)
    val timelineHandlers = TimelineHandlersBuilder().apply {
        plugins.map { it.timelineHandlers }.forEach { plusAssign(it) }
    }.build()

    CompositionLocalProvider(
        LocalConfiguration provides configurationProvider(),
        LocalRouter provides router,
        LocalPlugins provides plugins,
        LocalTimelineHandlers provides timelineHandlers,
    ) {
        MaterialTheme {
            Children(router.state) {
                it.instance()
            }
        }
    }
}
