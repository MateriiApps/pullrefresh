package dev.materii.pullrefresh

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
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
            modifier = Modifier.align(Alignment.Center)
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
                    if (flipped) Alignment.BottomStart else Alignment.TopStart
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

