package net.accelf.contral.core.plugin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import net.accelf.contral.core.router.Routes
import net.accelf.contral.core.ui.Renderable

data class Plugin(
    val id: String,
    val name: String,
    val routes: Routes,
) : Renderable {

    @Composable
    override fun render(ctx: ComponentContext) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = id,
                style = MaterialTheme.typography.caption,
            )
        }
    }
}

internal val LocalPlugins = staticCompositionLocalOf { emptyList<Plugin>() }
