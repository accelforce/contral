package net.accelf.contral.mastodon.api

import net.accelf.contral.mastodon.models.Status
import retrofit2.http.GET
import retrofit2.http.Query

interface Mastodon {

    @GET("/api/v1/timelines/public")
    suspend fun getPublicTimeline(
        @Query("max_id") maxId: String = "",
        @Query("min_id") minId: String = "",
    ): List<Status>
}
