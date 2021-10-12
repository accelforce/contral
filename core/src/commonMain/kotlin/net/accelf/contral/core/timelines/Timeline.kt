package net.accelf.contral.core.timelines

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import net.accelf.contral.core.router.PageComponent

val Timeline: PageComponent = { route, ctx ->
    val snackbarHostState = remember { SnackbarHostState() }
    val handlers = LocalTimelineHandlers.current

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
    ) {
        route.params["handler"]?.let { handlerName ->
            handlers[handlerName]?.invoke(route.params)?.let { handler ->
                var items by remember { mutableStateOf(emptyList<TimelineItem>()) }
                var refreshing by remember { mutableStateOf(false) }
                val scrollState = rememberLazyListState()
                val density = LocalDensity.current

                LaunchedEffect(refreshing) {
                    if (refreshing) {
                        val new = runCatching { handler.fetchTop(items.first()) }
                            .getOrElse {
                                snackbarHostState.showSnackbar(
                                    message = "error loading timeline: ${it.message}",
                                    duration = SnackbarDuration.Long,
                                )

                                refreshing = false
                                return@LaunchedEffect
                            }
                        if (new.isNotEmpty()) {
                            items = new + items
                            scrollState.scrollToItem(new.size)
                            scrollState.animateScrollBy(density.run { -50.dp.toPx() })
                        }
                        refreshing = false
                    }
                }

                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = refreshing),
                    onRefresh = { refreshing = true },
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        state = scrollState,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        items(items) { item ->
                            Card(
                                modifier = Modifier.padding(4.dp),
                                elevation = 4.dp,
                            ) {
                                item.render(ctx)
                            }
                        }

                        if (items.isEmpty()) {
                            item {
                                LaunchedEffect(Unit) {
                                    items = runCatching { handler.initialFetch() }
                                        .getOrElse {
                                            snackbarHostState.showSnackbar(
                                                message = "error loading timeline: ${it.message}",
                                                duration = SnackbarDuration.Long,
                                            )
                                            return@LaunchedEffect
                                        }
                                }
                            }
                        } else {
                            item {
                                LaunchedEffect(Unit) {
                                    val old = runCatching { handler.fetchBottom(items.last()) }
                                        .getOrElse {
                                            snackbarHostState.showSnackbar(
                                                message = "error loading timeline: ${it.message}",
                                                duration = SnackbarDuration.Long,
                                            )
                                            return@LaunchedEffect
                                        }
                                    items = items + old
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.padding(4.dp),
                                    )
                                }
                            }
                        }
                    }
                }
            } ?: LaunchedEffect(Unit) {
                snackbarHostState.showSnackbar(
                    message = "handler $handlerName not found",
                    duration = SnackbarDuration.Indefinite,
                )
            }
        } ?: LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(
                message = "handler not specified",
                duration = SnackbarDuration.Indefinite,
            )
        }
    }
}
