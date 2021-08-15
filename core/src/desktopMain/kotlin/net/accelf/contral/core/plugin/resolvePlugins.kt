package net.accelf.contral.core.plugin

import androidx.compose.runtime.Composable
import net.harawata.appdirs.AppDirsFactory
import okio.ExperimentalFileSystem
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import okio.openZip
import java.net.URLClassLoader

@OptIn(ExperimentalFileSystem::class)
@Composable
actual fun resolvePlugins(): List<Plugin> {
    val dir = (AppDirsFactory.getInstance()
        .getUserDataDir("contral", "alpha", "accelf", false)
        .toPath() / "plugins")
        .toFile()

    if (!dir.exists()) {
        dir.mkdirs()
        return emptyList()
    }

    val jarPaths = dir.listFiles { file -> file.extension == "jar" }!!
    if (jarPaths.isEmpty()) {
        return emptyList()
    }

    val loader = URLClassLoader(
        jarPaths.map { it.toURI().toURL() }.toTypedArray(),
        PluginResolver::class.java.classLoader,
    )
    val methods = jarPaths
        .map {
            FileSystem.SYSTEM.openZip(it.absolutePath.toPath())
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
