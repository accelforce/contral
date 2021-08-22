@file:JvmName("CommonConfigurationProviderKt")
package net.accelf.contral.core.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
internal expect fun configurationProvider(): ConfigurationProvider

val LocalConfiguration =
    staticCompositionLocalOf<ConfigurationProvider> { error("Configuration not set") }
