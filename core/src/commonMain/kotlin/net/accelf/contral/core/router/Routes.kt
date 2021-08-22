package net.accelf.contral.core.router

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext

typealias Page = (Route, ComponentContext) -> @Composable () -> Unit
typealias PageComponent = @Composable (Route, ComponentContext) -> Unit

fun PageComponent.toPage(): Page = { route, ctx -> { this(route, ctx) } }

typealias Routes = Map<String, Page>

class RoutesBuilder(
    initialRoutes: Routes = mapOf(),
) {

    private val routes = initialRoutes.toMutableMap()

    fun register(name: String, page: Page) {
        if (name in routes) {
            throw RouteExistsException(name)
        }

        routes[name] = page
    }

    @JvmName("registerPageComponent")
    fun register(name: String, pageComponent: PageComponent) = register(name, pageComponent.toPage())

    operator fun plusAssign(other: Routes) {
        other.forEach { (name, page) -> register(name, page) }
    }

    fun build(): Routes = routes

    class RouteExistsException(name: String) :
        IllegalArgumentException("Route $name already registered")
}

fun routesBuilder(
    func: RoutesBuilder.() -> Unit,
): RoutesBuilder =
    RoutesBuilder().apply { func() }
