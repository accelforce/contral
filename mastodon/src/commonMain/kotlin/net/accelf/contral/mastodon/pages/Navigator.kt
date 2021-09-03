package net.accelf.contral.mastodon.pages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.push
import net.accelf.contral.core.router.LocalRouter
import net.accelf.contral.core.router.PageComponent
import net.accelf.contral.core.router.Route

@OptIn(ExperimentalComposeUiApi::class)
val Navigator: PageComponent = { route, _ ->
    val router = LocalRouter.current
    var domain by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    route.params["error"]?.let { error ->
        LaunchedEffect(error) {
            snackbarHostState.showSnackbar(error)
        }
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            fun navigate() {
                router.push(Route("timeline", mapOf(
                    "handler" to "mastodon:public",
                    "domain" to domain,
                )))
            }

            OutlinedTextField(
                value = domain,
                onValueChange = { domain = it.trim() },
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f)
                    .onKeyEvent {
                        if (it.key == Key.Enter) {
                            navigate()
                            return@onKeyEvent true
                        }
                        return@onKeyEvent false
                    },
                keyboardActions = KeyboardActions(
                    onDone = { navigate() },
                ),
                singleLine = true,
            )

            Button(
                onClick = ::navigate,
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.CenterVertically),
            ) {
                Text(text = "Show")
            }
        }
    }
}
