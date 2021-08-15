package net.accelf.contral.core.plugin

import androidx.compose.runtime.Composable

@Composable
expect fun resolvePlugins(): List<Plugin>
