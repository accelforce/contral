package net.accelf.contral.core.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import net.accelf.contral.core.router.PageComponent
import net.accelf.contral.core.ui.Clicker
import net.accelf.contral.core.ui.Greeting

val Counter: PageComponent = { _, _ ->
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Greeting(name = "Clicker")
        Clicker()
    }
}
