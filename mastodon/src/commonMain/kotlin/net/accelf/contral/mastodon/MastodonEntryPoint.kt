package net.accelf.contral.mastodon

import net.accelf.contral.core.plugin.PluginResolver
import net.accelf.contral.mastodon.pages.Navigator
import net.accelf.contral.mastodon.timelines.PublicTimelineHandler

@Suppress("unused")
fun PluginResolver.mastodonPlugin() {
    name = "Mastodon"

    routes {
        register("navigator", Navigator)
    }

    timelineHandlers {
        register("mastodon:public") { params ->
            PublicTimelineHandler(
                domain = params["domain"] ?: error("domain not specified"),
            )
        }
    }
}
