package net.accelf.contral.android.config

import android.content.Context
import androidx.core.content.edit
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import net.accelf.contral.core.config.ConfigurationProvider

class AndroidConfigurationProvider(private val context: Context) : ConfigurationProvider() {

    override fun actualGet(group: String, key: String): JsonElement? =
        context.getSharedPreferences(group, Context.MODE_PRIVATE)
            .getString(key, null)
            ?.let { Json.decodeFromString(it) }

    override fun actualSet(group: String, key: String, value: JsonElement?) =
        context.getSharedPreferences(group, Context.MODE_PRIVATE)
            .edit {
                if (value != null) {
                    putString(key, Json.encodeToString(value))
                } else {
                    remove(key)
                }
            }
}
