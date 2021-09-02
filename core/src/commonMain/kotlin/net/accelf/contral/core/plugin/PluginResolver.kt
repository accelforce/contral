package net.accelf.contral.core.plugin

import net.accelf.contral.core.router.RoutesBuilder
import net.accelf.contral.core.timelines.TimelineHandlersBuilder

class PluginResolver(
    private val id: String,
) {
    var name: String? = null

    private val routesBuilder = RoutesBuilder()

    @Suppress("unused")
    fun routes(buildRoutes: RoutesBuilder.() -> Unit) {
        routesBuilder.apply(buildRoutes)
    }

    private val timelineHandlersBuilder = TimelineHandlersBuilder()

    @Suppress("unused")
    fun timelineHandlers(buildHandlers: TimelineHandlersBuilder.() -> Unit) {
        timelineHandlersBuilder.apply(buildHandlers)
    }

    internal fun build(): Plugin =
        Plugin(
            id = id,
            name = name ?: id,
            routes = routesBuilder.build(),
            timelineHandlers = timelineHandlersBuilder.build(),
        )
}
