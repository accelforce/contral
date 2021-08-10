package net.accelf.contral.core

import net.accelf.contral.core.pages.Counter
import net.accelf.contral.core.pages.Home
import net.accelf.contral.core.router.routesBuilder

val builtinRoutes = routesBuilder {
    register("", Home)
    register("counter", Counter)
}.build()
