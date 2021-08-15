package net.accelf.contral.core.plugin

class PluginResolver(
    private val id: String,
) {
    var name: String? = null

    internal fun build(): Plugin =
        Plugin(
            id = id,
            name = name ?: id,
        )
}
