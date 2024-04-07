package com.example.wordsfactory.presentation.ui.intro.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import com.example.wordsfactory.ui.theme.Grey
import com.example.wordsfactory.ui.theme.Secondary

// todo он находится не по центру =(
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomIndicator(
    fullWidth: Dp,
    count: Int,
    pagerState: PagerState,
    radius: CornerRadius,
    circleSpacing: Dp,
    activeLineWidth: Dp,
    width: Dp,
    height: Dp
) {
    Canvas(modifier = Modifier.width(width = fullWidth)) {
        val spacing = circleSpacing.toPx()
        val dotWidth = width.toPx()
        val dotHeight = height.toPx()

        val activeDotWidth = activeLineWidth.toPx()
        var x = 0f
        val y = center.y

        repeat(count) { i ->
            val posOffset = pagerState.pageOffset
            val dotOffset = posOffset % 1
            val current = posOffset.toInt()

            val factor = (dotOffset * (activeDotWidth - dotWidth))

            val calculatedWidth = when {
                i == current -> activeDotWidth - factor
                i - 1 == current || (i == 0 && posOffset > count - 1) -> dotWidth + factor
                else -> dotWidth
            }

            val color = if (i == pagerState.currentPage) Secondary else Grey
            drawIndicator(x, y, calculatedWidth, dotHeight, radius, color)
            x += calculatedWidth + spacing
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
val PagerState.pageOffset: Float
    get() = this.currentPage + this.currentPageOffsetFraction

private fun DrawScope.drawIndicator(
    x: Float, y: Float, width: Float, height: Float, radius: CornerRadius, color: Color
) {
    val rect = RoundRect(
        x, y - height / 2, x + width, y + height / 2, radius
    )
    val path = Path().apply { addRoundRect(rect) }
    drawPath(path = path, color = color)
}