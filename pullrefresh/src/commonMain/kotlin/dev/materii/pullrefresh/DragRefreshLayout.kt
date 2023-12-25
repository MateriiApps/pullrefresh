package dev.materii.pullrefresh

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity

/**
 * Layout for drag to refresh, dragging moves the [content] to reveal the [indicator] beneath
 *
 * @param state [PullRefreshState] used to updated the [content]'s position
 * @param modifier [Modifier] for the layout
 * @param contentAlignment How to align components within the [content]
 * @param flipped If true reveals the indicator on the bottom instead of the top, recommended to use this with the [pullRefresh] inverse parameter set to true
 * @param indicatorBackground Color to use for the revealed background, a darker version of the [content]'s background is recommended
 * @param indicator The indicator hidden beneath the [content], normally an arrow facing in the drag direction
 * @param content Content to be dragged
 */
@Deprecated(
    message = "Use the overload without `contentAlignment` instead",
    replaceWith = ReplaceWith(
        "DragRefreshBox(\n" +
            "state = state,\n" +
            "modifier = modifier,\n" +
            "flipped = flipped,\n" +
            "backdrop = indicatorBackground,\n" +
            "indicator = indicator,\n" +
            "content = content,\n" +
        ")"
    ),
    level = DeprecationLevel.HIDDEN
)
@Composable
fun DragRefreshLayout(
    state: PullRefreshState,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    flipped: Boolean = false,
    indicatorBackground: Color = Color.Black.copy(alpha = 0.3f),
    indicator: @Composable BoxScope.() -> Unit = {
        DragRefreshIndicator(
            state = state,
            color = Color.White,
            flipped = flipped,
        )
    },
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .background(indicatorBackground)
                .height(
                    with(LocalDensity.current) {
                        state.position.toDp()
                    }
                )
                .fillMaxWidth()
                .clip(RectangleShape)
                .align(
                    if (flipped) Alignment.BottomCenter else Alignment.TopCenter
                )
        ) {
            indicator()
        }

        Box(
            contentAlignment = contentAlignment,
            modifier = Modifier
                .graphicsLayer {
                    translationY = if (flipped) -state.position else state.position
                    clip = true
                    shape = RectangleShape
                }
        ) {
            content()
        }
    }
}

/**
 * Layout for drag to refresh, dragging moves the [content] to reveal the [indicator] beneath
 *
 * @param state [PullRefreshState] used to updated the [content]'s position
 * @param modifier [Modifier] for the layout
 * @param flipped If true reveals the indicator on the bottom instead of the top
 * @param backdropColor Color to use for the revealed background, a darker version of the [content]'s background is recommended
 * @param indicator The indicator hidden beneath the [content], normally an arrow facing in the drag direction
 * @param content Content to be dragged
 */
@Composable
fun DragRefreshLayout(
    state: PullRefreshState,
    modifier: Modifier = Modifier,
    flipped: Boolean = false,
    enabled: Boolean = true,
    indicator: @Composable () -> Unit = {
        DragRefreshIndicator(
            state = state,
            flipped = flipped
        )
    },
    backdropColor: Color = Color.Black.copy(alpha = 0.3f),
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .pullRefresh(
                state = state,
                inverse = flipped,
                enabled = enabled,
            )
            .then(modifier)
    ) {
        val density = LocalDensity.current
        val indicatorHeight by remember(density) {
            derivedStateOf {
                with(density) {
                    state.position.toDp()
                }
            }
        }
        val indicatorAlignment = if (flipped) Alignment.BottomCenter else Alignment.TopCenter
        Box(
            modifier = Modifier
                .background(backdropColor)
                .height(indicatorHeight)
                .fillMaxWidth()
                .align(indicatorAlignment),
            contentAlignment = Alignment.Center,
        ) {
            indicator()
        }

        Box(
            modifier = Modifier
                .graphicsLayer {
                    translationY = if (flipped) -state.position else state.position
                    clip = true
                    shape = RectangleShape
                },
        ) {
            content()
        }
    }
}