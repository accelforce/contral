package net.accelf.contral.mastodon.timelines

import net.accelf.contral.core.timelines.TimelineHandler
import net.accelf.contral.core.timelines.TimelineItem
import net.accelf.contral.mastodon.api.Mastodon
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class PublicTimelineHandler(domain: String) : TimelineHandler {

    private val mastodon = Retrofit.Builder()
        .baseUrl(
            HttpUrl.Builder()
                .scheme("https")
                .host(domain)
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create<Mastodon>()

    override suspend fun initialFetch(): List<TimelineItem> =
        mastodon.getPublicTimeline()

    override suspend fun fetchNext(oldestItem: TimelineItem): List<TimelineItem> =
        mastodon.getPublicTimeline(maxId = oldestItem.key)
}
