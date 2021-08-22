package net.accelf.contral.core.plugin

import androidx.compose.runtime.Composable

@Composable
internal expect fun resolvePlugins(): List<Plugin>
