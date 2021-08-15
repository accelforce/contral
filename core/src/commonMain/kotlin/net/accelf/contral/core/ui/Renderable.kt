package net.accelf.contral.core.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext

interface Renderable {
    @Composable
    fun render(ctx: ComponentContext)
}
