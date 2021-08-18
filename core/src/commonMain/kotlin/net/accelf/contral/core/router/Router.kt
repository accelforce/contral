package net.accelf.contral.core.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router
import net.accelf.contral.core.pages.errors.PageNotFoundPage

@Suppress("FunctionNaming")
fun Router(
    ctx: ComponentContext,
    defaultRoute: Route,
    routes: Routes,
) = Router(
    routes,
    ctx.router(
        initialConfiguration = defaultRoute,
        handleBackButton = true,
    ) { route, routerCtx ->
        (routes[route.name] ?: PageNotFoundPage)(route, routerCtx)
    },
)

open class Router(
    val routes: Routes,
    instance: com.arkivanov.decompose.Router<Route, @Composable () -> Unit>,
) : com.arkivanov.decompose.Router<Route, @Composable () -> Unit> by instance

val LocalRouter = staticCompositionLocalOf<Router> { error("Router not set") }
