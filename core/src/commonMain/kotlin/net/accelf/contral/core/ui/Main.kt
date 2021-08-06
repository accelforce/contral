package net.accelf.contral.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.accelf.contral.core.config.ConfigurationProvider
import net.accelf.contral.core.config.LocalConfiguration

@Composable
fun Main(
    name: String,
    configurationProvider: ConfigurationProvider,
) {
    CompositionLocalProvider(LocalConfiguration provides configurationProvider) {
        MaterialTheme {
            Column {
                Greeting(name = name)
                Clicker()
            }
        }
    }
}

@Composable
private fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

data class ClickerCount(
    val count: Int = 0
)

@Composable
private fun Clicker() {
    val config = LocalConfiguration.current
    val countState = remember { mutableStateOf(config get ClickerCount::count) }
    val setCount: (Int) -> Unit = {
        config.set(ClickerCount::count, it)
        countState.value = it
    }
    val count by countState

    Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { setCount(count + 1) },
        ) {
            Text(if (count == 0) "Hello World" else "Clicked $count!")
        }
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { setCount(0) },
        ) {
            Text("Reset")
        }
    }
}
