package net.accelf.contral.core.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.Router
import com.arkivanov.decompose.router
import net.accelf.contral.core.pages.errors.PageNotFoundPage

class Router(
    ctx: ComponentContext,
    defaultRoute: Route,
    routes: Routes,
) : ComponentContext by ctx {

    val instance = router(
        initialConfiguration = defaultRoute,
        handleBackButton = true,
    ) { route, ctx ->
        (routes[route.name] ?: PageNotFoundPage)(route, ctx)
    }

    val state = instance.state
}

val LocalRouter =
    staticCompositionLocalOf<Router<Route, @Composable () -> Unit>> { error("Router not set") }
