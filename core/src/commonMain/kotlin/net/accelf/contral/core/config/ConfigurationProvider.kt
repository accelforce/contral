package net.accelf.contral.core.config

import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

abstract class ConfigurationProvider {

    val instances: MutableMap<KClass<*>, Any> = mutableMapOf()

    inline fun <reified G> getInstance(): G =
        (instances[G::class] ?: let {
            val constructor = G::class.constructors.first()
            val instance = constructor.callBy(emptyMap())
            instances[G::class] = instance!!
            instance
        }) as G

    abstract fun actualGet(group: String, key: String): JsonElement?
    inline infix fun <reified G, reified V> get(prop: KProperty1<G, V>): V =
        actualGet(G::class.qualifiedName!!, prop.name)?.let {
            Json.decodeFromJsonElement(it)
        } ?: prop(getInstance())

    abstract fun actualSet(group: String, key: String, value: JsonElement?)
    inline fun <reified G, reified V> set(prop: KProperty1<G, V>, value: V) =
        actualSet(
            G::class.qualifiedName!!,
            prop.name,
            if (value == prop(getInstance())) {
                null
            } else {
                Json.encodeToJsonElement(value)
            },
        )
}

val LocalConfiguration =
    staticCompositionLocalOf<ConfigurationProvider> { error("Configuration not set") }
