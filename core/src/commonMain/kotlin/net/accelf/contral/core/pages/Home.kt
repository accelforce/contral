package net.accelf.contral.core.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.push
import net.accelf.contral.core.router.LocalRouter
import net.accelf.contral.core.router.PageComponent
import net.accelf.contral.core.router.Route
import net.accelf.contral.core.ui.Greeting

val Home: PageComponent = { _, _ ->
    val router = LocalRouter.current

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Greeting("HOME")
        Button(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterHorizontally),
            onClick = { router.push(Route("counter")) },
        ) {
            Text("Counter")
        }
        Button(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterHorizontally),
            onClick = { router.push(Route("plugins")) },
        ) {
            Text("Plugins")
        }
    }
}
