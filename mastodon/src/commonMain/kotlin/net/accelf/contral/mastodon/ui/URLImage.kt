package net.accelf.contral.mastodon.ui

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.kamel.image.KamelImage
import io.kamel.image.lazyPainterResource

@Composable
fun URLImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    KamelImage(
        resource = lazyPainterResource(url),
        contentDescription = contentDescription,
        modifier = modifier,
        onLoading = {
            Image(
                imageVector = Icons.Default.HourglassTop,
                contentDescription = contentDescription,
                modifier = modifier,
            )
        },
        onFailure = {
            Image(
                imageVector = Icons.Default.Error,
                contentDescription = contentDescription,
                modifier = modifier,
            )
        },
    )
}
