package net.accelf.contral.core.config

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import net.harawata.appdirs.AppDirsFactory
import java.io.File
import kotlin.io.path.Path

class DesktopConfigurationProvider : ConfigurationProvider() {

    private val dir = AppDirsFactory.getInstance().getUserConfigDir("contral", "alpha", "accelf", true)

    override fun actualGet(group: String, key: String): JsonElement? {
        val file = file(group)
        if (!file.canRead()) {
            return null
        }
        val obj = Json.parseToJsonElement(file.readText()).jsonObject
        return obj[key]
    }

    override fun actualSet(group: String, key: String, value: JsonElement?) {
        val file = file(group)

        val obj = if (!file.exists()) {
            if (value == null) {
                return
            }

            file.parentFile?.mkdirs()
            file.createNewFile()
            emptyMap()
        } else {
            file.readText().takeUnless { it.isEmpty() }
                ?.let { Json.parseToJsonElement(it).jsonObject }
                ?: emptyMap()
        }

        val new = if (value == null) {
            if (!obj.containsKey(key)) {
                return
            }

            obj.filter { (k, _) -> k != key }
        } else {
            obj + (key to value)
        }

        if (new.isEmpty()) {
            file.delete()
        } else {
            file.writeText(Json.encodeToString(JsonObject(new)))
        }
    }

    private fun file(group: String): File = Path(dir, "$group.json").toFile()
}
