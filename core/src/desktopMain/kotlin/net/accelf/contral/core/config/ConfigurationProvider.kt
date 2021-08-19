package net.accelf.contral.core.config

import androidx.compose.runtime.Composable

@Composable
actual fun configurationProvider(): ConfigurationProvider = DesktopConfigurationProvider()
