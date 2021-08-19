@file:JvmName("CommonConfigurationProviderKt")
package net.accelf.contral.core.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
expect fun configurationProvider(): ConfigurationProvider

val LocalConfiguration =
    staticCompositionLocalOf<ConfigurationProvider> { error("Configuration not set") }
