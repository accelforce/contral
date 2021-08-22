package net.accelf.contral.core.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import net.accelf.contral.core.plugin.LocalPlugins
import net.accelf.contral.core.router.PageComponent

internal val Plugins: PageComponent = { _, ctx ->
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        LocalPlugins.current.forEach { plugin ->
            plugin.render(ctx)
        }
    }
}
