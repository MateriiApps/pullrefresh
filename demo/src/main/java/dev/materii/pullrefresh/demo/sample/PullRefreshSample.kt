package dev.materii.pullrefresh.demo.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.materii.pullrefresh.PullRefreshIndicator
import dev.materii.pullrefresh.PullRefreshState

@Composable
fun PullRefreshSample(
    flipped: Boolean,
    pullRefreshState: PullRefreshState,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Pull ${if (flipped) "up" else "down"} to refresh")

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            flipped = flipped,
            backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(
                    if (flipped) Alignment.BottomCenter else Alignment.TopCenter
                )
        )
    }
}