package net.accelf.contral.mastodon.api

import net.accelf.contral.mastodon.models.Status
import retrofit2.http.GET

interface Mastodon {

    @GET("/api/v1/timelines/public")
    suspend fun getPublicTimeline(): List<Status>
}
