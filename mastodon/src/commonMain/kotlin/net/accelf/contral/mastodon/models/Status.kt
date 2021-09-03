package net.accelf.contral.mastodon.models

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import kotlinx.datetime.Instant
import net.accelf.contral.core.timelines.TimelineItem
import net.accelf.contral.mastodon.ui.Html
import net.accelf.contral.mastodon.ui.HtmlAnnotations
import net.accelf.contral.mastodon.ui.HtmlText
import net.accelf.contral.mastodon.ui.URLImage

data class Status(
    val id: String,
    val account: Account,
    val content: Html,
    val createdAt: Instant,
) : TimelineItem {

    override val key: String
        get() = id

    @Composable
    override fun render(ctx: ComponentContext) {
        val uriHandler = LocalUriHandler.current

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            URLImage(
                url = account.avatar,
                modifier = Modifier
                    .padding(4.dp)
                    .size(48.dp),
            )

            Column {
                Row(
                    modifier = Modifier.padding(4.dp),
                ) {
                    Text(
                        text = account.displayName,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        maxLines = 1,
                        style = MaterialTheme.typography.subtitle1,
                    )

                    Text(
                        text = "@${account.acct}",
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .align(Alignment.CenterVertically),
                        maxLines = 1,
                        style = MaterialTheme.typography.subtitle2,
                    )
                }

                HtmlText(
                    html = content,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
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
    }
}
