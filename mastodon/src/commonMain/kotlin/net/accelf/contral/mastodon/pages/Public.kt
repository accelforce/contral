package net.accelf.contral.mastodon.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.accelf.contral.core.router.PageComponent
import net.accelf.contral.mastodon.api.Mastodon
import net.accelf.contral.mastodon.models.Status
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@OptIn(ExperimentalComposeUiApi::class)
val Public: PageComponent = { _, ctx ->
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        var statuses by remember { mutableStateOf(emptyList<Status>()) }

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            var domain by remember { mutableStateOf("") }
            val scope = rememberCoroutineScope()
            val refresh: () -> Unit = {
                scope.launch {
                    val mastodon = Retrofit.Builder()
                        .baseUrl(
                            HttpUrl.Builder()
                                .scheme("https")
                                .host(domain)
                                .build()
                        )
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create<Mastodon>()
                    statuses = mastodon.getPublicTimeline()
                }
            }

            OutlinedTextField(
                value = domain,
                onValueChange = { domain = it },
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f)
                    .onKeyEvent {
                        if (it.key == Key.Companion.Enter) {
                            refresh()
                            return@onKeyEvent true
                        }
                        return@onKeyEvent false
                    },
                keyboardActions = KeyboardActions(
                    onDone = { refresh() },
                ),
                singleLine = true,
            )

            Button(
                onClick = refresh,
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.CenterVertically),
            ) {
                Text(text = "Fetch")
            }
        }

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
        ) {
            statuses.forEach { status ->
                Card(
                    modifier = Modifier.padding(4.dp),
                    elevation = 4.dp,
                ) {
                    status.render(ctx)
                }
            }
        }
    }
}
