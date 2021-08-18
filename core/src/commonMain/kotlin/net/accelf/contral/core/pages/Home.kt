package net.accelf.contral.core.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.push
import net.accelf.contral.core.router.LocalRouter
import net.accelf.contral.core.router.PageComponent
import net.accelf.contral.core.router.Route
import net.accelf.contral.core.ui.Greeting
import net.accelf.contral.core.ui.components.Dropdown

val Home: PageComponent = { _, _ ->
    val router = LocalRouter.current

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Greeting("HOME")

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            var selected by remember { mutableStateOf<String?>(null) }

            Dropdown(
                items = router.routes.map { (name, _) -> name },
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f),
                selected = selected,
                onChange = { selected = it },
            )

            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.CenterVertically),
                onClick = { selected?.let { router.push(Route(it)) } },
                enabled = selected != null,
            ) {
                Text("Go!")
            }
        }
    }
}
