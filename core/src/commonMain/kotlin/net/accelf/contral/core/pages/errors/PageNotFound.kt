package net.accelf.contral.core.pages.errors

import androidx.compose.material.Text
import net.accelf.contral.core.router.PageComponent
import net.accelf.contral.core.router.toPage

private val PageNotFound: PageComponent = { route, _ ->
    Text("Page not found: ${route.name}")
}

val PageNotFoundPage = PageNotFound.toPage()
