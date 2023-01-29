package cn.yx.pfun.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

//单个波浪
class OneWaveShape(
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        var rect = Rect(
            0f, 0f, size.width, size.height
        )
        var path = Path();
        path.moveTo(rect.left, rect.top)
        path.quadraticBezierTo(
            rect.left + size.width / 2, rect.bottom,
            rect.right, rect.top
        )
        path.close()
        return Outline.Generic(path)
    }
}

//半圆
class HalfCircleShape(
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        var rect = Rect(
            0f, -size.height, size.width, size.height
        )
        var path = Path();
        path.moveTo(0f,0f)
        path.addArc(rect,0f,180f)
        return Outline.Generic(path)
    }
}

//tag bg
class TagBackgroundShape(var arcHeight:Float
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        if(arcHeight>size.height){
            arcHeight=size.height
        }
        var arcTop=size.height-arcHeight*2
        var rect = Rect(
            0f, arcTop, size.width, size.height
        )
        var path = Path();
        path.moveTo(size.width,0f)
        path.lineTo(size.width,arcTop)
        path.arcTo(rect,0f,180f,false)
        path.lineTo(0f,0f)
        path.close()
        return Outline.Generic(path)
    }
}