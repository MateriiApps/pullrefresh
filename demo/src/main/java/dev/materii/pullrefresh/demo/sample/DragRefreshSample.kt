package dev.materii.pullrefresh.demo.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.materii.pullrefresh.DragRefreshIndicator
import dev.materii.pullrefresh.DragRefreshLayout
import dev.materii.pullrefresh.PullRefreshState

@Composable
fun DragRefreshSample(
    flipped: Boolean,
    pullRefreshState: PullRefreshState,
    modifier: Modifier = Modifier
) {
    DragRefreshLayout(
        modifier = modifier,
        state = pullRefreshState,
        flipped = flipped,
        indicator = {
            DragRefreshIndicator(
                state = pullRefreshState,
                flipped = flipped,
                color = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Pull ${if (flipped) "up" else "down"} to refresh")
        }
    }
}