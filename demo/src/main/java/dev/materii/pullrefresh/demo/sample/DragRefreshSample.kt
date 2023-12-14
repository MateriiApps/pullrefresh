package dev.materii.pullrefresh.demo.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.materii.pullrefresh.DragRefreshLayout
import dev.materii.pullrefresh.PullRefreshState

@Composable
fun DragRefreshSample(
    flipped: Boolean,
    pullRefreshState: PullRefreshState,
    modifier: Modifier = Modifier
) {
    DragRefreshLayout(
        state = pullRefreshState,
        flipped = flipped,
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "Pull ${if (flipped) "up" else "down"} to refresh")
        }
    }
}