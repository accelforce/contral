package net.accelf.contral.mastodon.models

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import kotlinx.datetime.Instant
import net.accelf.contral.core.ui.Renderable

typealias Html = String

data class Status(
    val id: String,
    val content: Html,
    val createdAt: Instant,
) : Renderable {

    @Composable
    override fun render(ctx: ComponentContext) {
        Text(
            text = content,
        )
    }
}
