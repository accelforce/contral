package net.accelf.contral.core.plugin

import net.accelf.contral.core.router.RoutesBuilder

class PluginResolver(
    private val id: String,
) {
    var name: String? = null

    private val routesBuilder = RoutesBuilder()

    @Suppress("unused")
    fun routes(buildRoutes: RoutesBuilder.() -> Unit) {
        routesBuilder.apply(buildRoutes)
    }

    internal fun build(): Plugin =
        Plugin(
            id = id,
            name = name ?: id,
            routes = routesBuilder.build(),
        )
}
