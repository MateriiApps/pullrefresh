package dev.materii.pullrefresh.demo.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.materii.pullrefresh.DragRefreshIndicator
import dev.materii.pullrefresh.DragRefreshLayout
import dev.materii.pullrefresh.PullRefreshState

@Composable
fun DragRefreshSample(
    flipped: Boolean,
    pullRefreshState: PullRefreshState,
    modifier: Modifier = Modifier,
    isRefreshing: Boolean
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
        LazyColumn(
            Modifier.fillMaxSize(),
            userScrollEnabled = !isRefreshing
        ) {
            items(100) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .background(
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(10.dp), text = "No. $it"
                )
            }
        }
    }
}