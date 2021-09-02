package net.accelf.contral.mastodon

import net.accelf.contral.core.plugin.PluginResolver
import net.accelf.contral.mastodon.pages.Navigator
import net.accelf.contral.mastodon.pages.Public

@Suppress("unused")
fun PluginResolver.mastodonPlugin() {
    name = "Mastodon"

    routes {
        register("navigator", Navigator)
        register("public", Public)
    }
}
