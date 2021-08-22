package net.accelf.contral.core.config

import androidx.compose.runtime.Composable

@Composable
internal actual fun configurationProvider(): ConfigurationProvider = DesktopConfigurationProvider()
