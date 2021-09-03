package net.accelf.contral.mastodon.models

import com.google.gson.annotations.SerializedName

data class Account(
    val id: String,
    val acct: String,
    val username: String,
    @SerializedName("display_name") val nullableDisplayName: String,
    val avatar: String,
) {

    val displayName
        get() = nullableDisplayName.takeUnless(String::isEmpty) ?: username
}
