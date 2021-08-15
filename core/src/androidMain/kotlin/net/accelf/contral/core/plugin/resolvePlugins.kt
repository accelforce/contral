package net.accelf.contral.core.plugin

import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dalvik.system.DexClassLoader
import okio.ExperimentalFileSystem
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import okio.openZip

@OptIn(ExperimentalFileSystem::class)
@Composable
actual fun resolvePlugins(): List<Plugin> {
    val context = LocalContext.current
    val apkPaths = context.packageManager
        .getInstalledApplications(PackageManager.GET_META_DATA)
        .filter { it.metaData?.getBoolean("contralPlugin", false) ?: false }
        .map { it.publicSourceDir }
    if (apkPaths.isEmpty()) {
        return emptyList()
    }

    val loader = DexClassLoader(
        apkPaths.joinToString(":"),
        null,
        null,
        PluginResolver::class.java.classLoader,
    )
    val methods = apkPaths
        .map {
            FileSystem.SYSTEM.openZip(it.toPath())
                .source("contral".toPath())
                .buffer()
                .readUtf8()
                .lines()
        }
        .flatten()
        .filter(String::isNotBlank)
        .map {
            val index = it.lastIndexOf('.')
            Triple(it, it.substring(0, index), it.substring(index + 1))
        }
    return methods
        .map { (id, className, methodName) ->
            PluginResolver(id)
                .apply {
                    loader.loadClass(className)
                        .getMethod(methodName, PluginResolver::class.java)
                        .invoke(null, this)
                }
                .build()
        }
}
