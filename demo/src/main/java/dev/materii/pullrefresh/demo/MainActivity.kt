package dev.materii.pullrefresh.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import dev.materii.pullrefresh.demo.sample.DragRefreshSample
import dev.materii.pullrefresh.demo.sample.PullRefreshSample
import dev.materii.pullrefresh.demo.theme.SwipeRefreshTheme
import dev.materii.pullrefresh.pullRefresh
import dev.materii.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            var flipped by remember { mutableStateOf(false) }
            val pagerState = rememberPagerState { 2 }
            val scope = rememberCoroutineScope()
            var isRefreshing by remember {
                mutableStateOf(false)
            }
            val pullRefreshState = rememberPullRefreshState(
                refreshing = isRefreshing,
                onRefresh = {
                    scope.launch {
                        isRefreshing = true
                        delay(3_000)
                        isRefreshing = false
                    }
                }
            )

            SwipeRefreshTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                            ),
                            actions = {
                                IconToggleButton(
                                    checked = flipped,
                                    onCheckedChange = { checked -> flipped = checked },
                                    colors = IconButtonDefaults.iconToggleButtonColors(
                                        checkedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                                            alpha = 0.5f
                                        )
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Flip,
                                        contentDescription = "Flip"
                                    )
                                }
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.padding(it)
                    ) {
                        PrimaryTabRow(
                            selectedTabIndex = pagerState.currentPage,
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                            divider = {}
                        ) {
                            Tab(
                                text = { Text("Pull to refresh") },
                                selected = pagerState.currentPage == 0,
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(0)
                                    }
                                }
                            )

                            Tab(
                                text = { Text("Drag to refresh") },
                                selected = pagerState.currentPage == 1,
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(1)
                                    }
                                }
                            )
                        }

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxSize()
                        ) { page ->
                            when (page) {
                                0 -> PullRefreshSample(
                                    flipped = flipped,
                                    pullRefreshState = pullRefreshState,
                                    modifier = Modifier.fillMaxSize(),
                                )

                                1 -> DragRefreshSample(
                                    flipped = flipped,
                                    pullRefreshState = pullRefreshState,
                                    modifier = Modifier.fillMaxSize(),
                                    isRefreshing = isRefreshing
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}