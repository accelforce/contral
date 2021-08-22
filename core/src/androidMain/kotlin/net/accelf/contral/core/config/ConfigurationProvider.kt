package net.accelf.contral.core.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
internal actual fun configurationProvider(): ConfigurationProvider = AndroidConfigurationProvider(LocalContext.current)
