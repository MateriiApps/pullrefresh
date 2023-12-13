package dev.materii.pullrefresh.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import dev.materii.pullrefresh.DragRefreshLayout
import dev.materii.pullrefresh.PullRefreshIndicator
import dev.materii.pullrefresh.demo.theme.SwipeRefreshTheme
import dev.materii.pullrefresh.demo.theme.applyTonalElevation
import dev.materii.pullrefresh.rememberPullRefreshState
import dev.materii.pullrefresh.pullRefresh
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val scope = rememberCoroutineScope()
            var isRefreshing by remember {
                mutableStateOf(false)
            }
            val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
                scope.launch {
                    isRefreshing = true
                    delay(3_000)
                    isRefreshing = false
                }
            })

            SwipeRefreshTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.applyTonalElevation(
                                    backgroundColor = MaterialTheme.colorScheme.surface,
                                    elevation = 3.dp
                                )
                            )
                        )
                    },
                    contentWindowInsets = WindowInsets(0),
                    modifier = Modifier.pullRefresh(pullRefreshState, inverse = true)
                ) {
                    DragRefreshLayout(
                        state = pullRefreshState,
                        flipped = true,
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(text = "Pull up to refresh")

//                        PullRefreshIndicator(
//                            refreshing = isRefreshing,
//                            state = pullRefreshState,
//                            backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
//                            contentColor = MaterialTheme.colorScheme.primary,
//                            modifier = Modifier.align(Alignment.TopCenter)
//                        )
                        }
                    }
                }
            }
        }
    }
}