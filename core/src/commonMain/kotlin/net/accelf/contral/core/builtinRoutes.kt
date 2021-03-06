package net.accelf.contral.core

import net.accelf.contral.core.pages.Counter
import net.accelf.contral.core.pages.Home
import net.accelf.contral.core.pages.Plugins
import net.accelf.contral.core.router.routesBuilder
import net.accelf.contral.core.timelines.Timeline

internal val builtinRoutes = routesBuilder {
    register("home", Home)
    register("counter", Counter)
    register("plugins", Plugins)
    register("timeline", Timeline)
}
