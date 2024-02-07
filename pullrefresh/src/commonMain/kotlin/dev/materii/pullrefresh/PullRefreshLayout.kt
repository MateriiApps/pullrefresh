package dev.materii.pullrefresh

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Layout for pull to refresh
 *
 * @param state [PullRefreshState]
 * @param modifier [Modifier] for the layout
 * @param flipped If true reveals the indicator on the bottom instead of the top
 * @param indicator [PullRefreshIndicator]
 * @param content The content of the box
 */
@Composable
fun PullRefreshLayout(
    state: PullRefreshState,
    modifier: Modifier = Modifier,
    flipped: Boolean = false,
    enabled: Boolean = true,
    indicator: @Composable () -> Unit = {
        PullRefreshIndicator(
            state = state,
            flipped = flipped
        )
    },
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .pullRefresh(
                state = state,
                inverse = flipped,
                enabled = enabled
            )
            .then(modifier)
    ) {
        content()

        val indicatorAlignment = if (flipped) Alignment.BottomCenter else Alignment.TopCenter
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(indicatorAlignment),
            contentAlignment = Alignment.Center
        ) {
            indicator()
        }
    }
}