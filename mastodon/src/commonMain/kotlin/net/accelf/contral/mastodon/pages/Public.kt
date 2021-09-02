package net.accelf.contral.mastodon.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.push
import kotlinx.coroutines.launch
import net.accelf.contral.core.router.LocalRouter
import net.accelf.contral.core.router.PageComponent
import net.accelf.contral.core.router.Route
import net.accelf.contral.mastodon.api.Mastodon
import net.accelf.contral.mastodon.models.Status
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val Public: PageComponent = component@{ route, ctx ->
    val router = LocalRouter.current
    val url = runCatching {
        HttpUrl.Builder()
            .scheme("https")
            .host(route.params["domain"] ?: error("domain not specified"))
            .build()
    }
        .getOrElse {
            router.push(Route("navigator", mapOf("error" to (it.message ?: ""))))
            return@component
        }
    val mastodon = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create<Mastodon>()
    var statuses by remember { mutableStateOf(emptyList<Status>()) }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(statuses) { status ->
                Card(
                    modifier = Modifier.padding(4.dp),
                    elevation = 4.dp,
                ) {
                    status.render(ctx)
                }
            }

            if (statuses.isEmpty()) {
                item {
                    val scope = rememberCoroutineScope()

                    SideEffect {
                        scope.launch {
                            statuses = runCatching { mastodon.getPublicTimeline() }
                                .getOrElse {
                                    router.push(Route("navigator", mapOf("error" to (it.message ?: ""))))
                                    return@launch
                                }
                        }
                    }
                }
            } else {
                item {
                    val scope = rememberCoroutineScope()

                    SideEffect {
                        scope.launch {
                            val old = runCatching { mastodon.getPublicTimeline(statuses.last().id) }
                                .getOrElse {
                                    router.push(Route("navigator", mapOf("error" to (it.message ?: ""))))
                                    return@launch
                                }
                            statuses = statuses + old
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(4.dp),
                        )
                    }
                }
            }
        }
    }
}
