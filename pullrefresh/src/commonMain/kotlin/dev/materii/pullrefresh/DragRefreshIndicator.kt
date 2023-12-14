package dev.materii.pullrefresh

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.drawscope.DrawScope
import dev.materii.pullrefresh.LoadingIndicatorDefaults.StrokeWidth

/**
 * Down arrow indicator for use in a [DragRefreshLayout]
 *
 * @param state The [PullRefreshState] used to properly scale the drawn indicator.
 * @param modifier Modifiers for the indicator.
 * @param color Color for the indicator
 * @param flipped Flips the arrow vertically
 */
@Composable
fun DragRefreshIndicator(
    state: PullRefreshState,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    flipped: Boolean = false
) {
    Crossfade(
        targetState = state.refreshing,
        animationSpec = tween(durationMillis = CrossfadeDurationMs),
        label = "Refresh",
        modifier = modifier
    ) { refreshing ->
        val spinnerSize = (ArcRadius + StrokeWidth).times(2)

        if (refreshing) {
            CircularLoadingIndicator(
                color = color,
                strokeWidth = StrokeWidth * (2f / 3),
                modifier = Modifier.size(spinnerSize)
            )
        } else {
            val arrowScale = LinearOutSlowInEasing
                .transform(state.position / state.threshold)
                .coerceIn(0f, 1f)

            ArrowIcon(
                color = color,
                flipped = flipped,
                modifier = Modifier
                    .size(spinnerSize)
                    .scale(arrowScale)
            )
        }
    }
}

/**
 * Displays a simple pointed arrow graphic
 *
 * @param color The color to use for this arrow
 * @param modifier [Modifier] to apply to this arrow
 * @param flipped Whether this arrow should point up instead of down
 */
@Composable
private fun ArrowIcon(
    color: Color,
    modifier: Modifier = Modifier,
    flipped: Boolean = false
) {
    val path = remember { Path().apply { fillType = PathFillType.EvenOdd } }

    Canvas(
        modifier = modifier
            .rotate(if (flipped) 180f else 0f)
    ) {
        val stemWidth = size.width / 4f
        val stemHeight = size.height * (2f / 3)
        val stemOffsetX = (size.width / 2f) - (stemWidth / 2f)

        val pointWidth = size.width * (2f / 3)
        val pointHeight = size.height - stemHeight
        val pointOffsetX = (size.width / 2f) - (pointWidth / 2f)

        drawRect(
            color = color,
            topLeft = Offset(stemOffsetX, 0f),
            size = Size(stemWidth, stemHeight)
        )

        drawTriangle(
            triangle = path,
            topLeft = Offset(pointOffsetX, stemHeight),
            size = Size(pointWidth, pointHeight),
            color = color
        )
    }
}

/**
 * Draws a pointed triangle, similar to ▼
 *
 * @param triangle Path to use when drawing this triangle
 * @param topLeft Offset from the local origin of 0, 0 relative to the current translation
 * @param size The size of this triangle
 * @param color Color to use for the triangles fill
 * @param alpha Alpha channel for this triangle
 */
private fun DrawScope.drawTriangle(
    triangle: Path,
    topLeft: Offset,
    size: Size,
    color: Color,
    alpha: Float = 1f
) {
    triangle.reset()
    triangle.moveTo(0f, 0f) // Start drawing at (0, 0)

    triangle.lineTo(size.width, 0f) // Draw from top left to top right
    triangle.lineTo(size.width / 2, size.height) // Draws diagonally from top right to bottom center
    triangle.close() // Completes the triangle (Draws a line from bottom center to top left)

    triangle.translate(topLeft)

    drawPath(path = triangle, color = color, alpha = alpha) // Draws the triangle to the canvas
}