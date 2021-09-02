package net.accelf.contral.core.router

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class Route(
    val name: String,
    val params: Map<String, String> = emptyMap(),
) : Parcelable
