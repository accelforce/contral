package net.accelf.contral.core.timelines

import androidx.compose.runtime.staticCompositionLocalOf
import net.accelf.contral.core.ui.Renderable

interface TimelineHandler {
    suspend fun initialFetch(): List<TimelineItem>
    suspend fun fetchNext(oldestItem: TimelineItem): List<TimelineItem>
}

interface TimelineItem : Renderable {
    val key: String
}

internal val LocalTimelineHandlers = staticCompositionLocalOf<TimelineHandlers> { emptyMap() }
