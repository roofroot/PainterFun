package cn.yx.pfun.ui.elements

import androidx.annotation.Px
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import cn.yx.pfun.SCREEN_DENSITY
import cn.yx.pfun.data.TipsEntity

@Composable
fun tipsViewRoot(list: List<TipsEntity>) {
    list.forEach {
        tipView(tip = it)
    }
}

@Composable
fun tipView(tip: TipsEntity) {
    Canvas(modifier = Modifier
        .offset((tip.lineStart.x / SCREEN_DENSITY).dp, (tip.lineStart.y / SCREEN_DENSITY).dp)
        .wrapContentWidth()
        .wrapContentHeight(),
        onDraw = {
            var rect =
                Rect(0f, 0f, tip.lineEnd.x - tip.lineStart.x, tip.lineEnd.y - tip.lineStart.y)

            drawLine(
                tip.colors.colorFg,
                Offset(rect.left, rect.top),
                Offset(rect.left + rect.width / 2, rect.top),
                strokeWidth = 3 * SCREEN_DENSITY,
            )
            drawLine(
                color = tip.colors.colorFg,
                Offset(rect.left + rect.width / 2, rect.top),
                Offset(rect.left + rect.width / 2, rect.bottom),
                strokeWidth = 3 * SCREEN_DENSITY,
            )
            drawLine(
                tip.colors.colorFg,
                Offset(rect.left + rect.width / 2, rect.bottom),
                Offset(rect.right, rect.bottom),
                strokeWidth = 3 * SCREEN_DENSITY,
            )

        })
    Text(
        text = tip.name,
        Modifier.offset((tip.lineEnd.x / SCREEN_DENSITY).dp, (tip.lineEnd.y / SCREEN_DENSITY).dp)
    )
}