package net.accelf.contral.mastodon.models

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import com.arkivanov.decompose.ComponentContext
import kotlinx.datetime.Instant
import net.accelf.contral.core.timelines.TimelineItem
import net.accelf.contral.mastodon.ui.Html
import net.accelf.contral.mastodon.ui.HtmlAnnotations
import net.accelf.contral.mastodon.ui.HtmlText

data class Status(
    val id: String,
    val content: Html,
    val createdAt: Instant,
) : TimelineItem {

    override val key: String = id

    @Composable
    override fun render(ctx: ComponentContext) {
        val uriHandler = LocalUriHandler.current

        HtmlText(
            html = content,
            modifier = Modifier.fillMaxWidth(),
        ) { annotations ->
            annotations.forEach { annotation ->
                when (annotation.tag) {
                    HtmlAnnotations.URL.tag -> {
                        uriHandler.openUri(annotation.item)
                        return@HtmlText
                    }
                }
            }
        }
    }
}
