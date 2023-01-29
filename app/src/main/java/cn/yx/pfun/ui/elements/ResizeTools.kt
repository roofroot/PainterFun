package cn.yx.pfun.ui.elements

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cn.yx.pfun.R
import cn.yx.pfun.SCREEN_DENSITY
import cn.yx.pfun.SCREEN_W
import cn.yx.pfun.data.PanelSize
import cn.yx.pfun.tools.getGCD




@Composable
fun resizePanel() {
    resizePanelIsExpanded = remember { mutableStateOf(false) }
    if (resizePanelIsExpanded.value) {
        Column() {
            previewPanel()
            Row() {
                Text(text = "窗口宽度：")
                progessBar(
                    width = (SCREEN_W - 200 * SCREEN_DENSITY).toInt(),
                    callback = {
                        Log.e("progress", "progess current value${it}")
                        resizePanelSize.w = it.toFloat()
                        getPreviewSize(resizePanelSize)
                    },
                    currentValue = resizePanelSize.w.toFloat(),
                    btnSize = 20.dp,
                    gross = maxRealPx.toInt(),
                    minValue = minPxW.toInt()
                )
            }
            Row() {
                Text(text = "窗口高度：")
                progessBar(
                    width = (SCREEN_W - 200 * SCREEN_DENSITY).toInt(),
                    callback = {
                        Log.e("progress", "progess current value${it}")
                        resizePanelSize.h = it.toFloat()
                        getPreviewSize(resizePanelSize)
                    },
                    currentValue = resizePanelSize.h.toFloat(),
                    btnSize = 20.dp,
                    gross = maxRealPx.toInt(),
                    minValue = minPxH.toInt()
                )
            }

            Button(onClick = {
                panelSize.h = resizePanelSize.h
                panelSize.w = resizePanelSize.w
                boxHeight.value = (resizePanelSize.h / SCREEN_DENSITY).dp
                boxWidth.value = (resizePanelSize.w / SCREEN_DENSITY).dp
                resizePanelIsExpanded.value = false
            }) {
                Text(text = "确定")
            }

        }
    }


}

@Composable
fun previewPanel() {
    previewHeight = remember {
        mutableStateOf(50.dp)
    }
    previewWidth = remember {
        mutableStateOf(50.dp)
    }
    Box(
        Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(20.dp)
    ) {
        Log.e("width_height", previewWidth.value.toString() + "," + previewHeight.value.toString())

        Column(
            Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .align(Alignment.BottomStart),
        ) {
            Text(
                "窗口宽度 w:" + resizePanelSize.w,
                color = Color.Blue,
                textAlign = TextAlign.Center
            )
            Text(
                "窗口高度 h:" + resizePanelSize.h,
                color = Color.Blue,
                textAlign = TextAlign.Center
            )
            var gcd = getGCD(resizePanelSize.w.toInt(), resizePanelSize.h.toInt())
            var w = resizePanelSize.w / gcd
            var h = resizePanelSize.h / gcd
            Text(
                "窗口比例 w/h : ${w}/${h}",
                color = Color.Blue,
                textAlign = TextAlign.Center
            )

        }

        Box(

            Modifier
                .width(maxPreviewW.dp)
                .height(maxPreviewH.dp)
                .align(Alignment.TopEnd)
                .border(3.dp, list[selectedPos.value].pairColors.colorBg),
        ) {
            Box(
                Modifier
                    .width(previewWidth.value)
                    .height(previewHeight.value)
                    .align(Alignment.Center)
                    .background(list[selectedPos.value].pairColors.colorBg),
            ) {

            }
        }

    }

}


//fun getPreviewSize(size: PanelSize) {
//    var w = 0f
//    var h = 0f
//    Log.e("getPreviewSize", "size:" + size.w + "," + size.h)
//    if (size.w > size.h) {
//        w = maxPreviewH
//        h = (size.h / size.w * w)
//        previewHeight.value = h.dp
//        previewWidth.value = w.dp
//        Log.e("getPreviewSize1", "w,h:" + w + "," + h)
//    } else {
//        h = maxPreviewW
//        w = (size.w / size.h * h)
//        previewWidth.value = w.dp
//        previewHeight.value = h.dp
//        Log.e("getPreviewSize2", "w,h:" + w + "," + h)
//    }
//}
fun getPreviewSize(size: PanelSize) {
    var w = 0f
    var h = 0f
    Log.e("getPreviewSize", "size:" + size.w + "," + size.h)

    w = maxPreviewW / maxRealPx * size.w
    h = maxPreviewH / maxRealPx * size.h
    previewWidth.value = w.dp
    previewHeight.value = h.dp
    Log.e("getPreviewSize2", "w,h:" + w + "," + h)
}


@Composable
fun progessBar(
    width: Int,
    callback: (currentValue: Int) -> Unit,
    currentValue: Float,
    btnSize: Dp,
    iconRes: Int = R.drawable.progress_mark,
    gross: Int,
    minValue: Int,
) {
    val gross = gross - minValue
    val iconSize = btnSize.value * SCREEN_DENSITY
//    Log.e("progess ", "width:" +  (width - iconSize))
//    Log.e("progess ", "w:" +  currentValue/gross)
    var offset = currentValue / gross * (width - iconSize)

    if (offset > width - iconSize) {
        offset = width - iconSize
    }
//   var offset = (iconOffset +colorInit.toFloat()) / 255f

    var offsetX: MutableState<Float> = remember {
        mutableStateOf(offset)
    }
    Box(modifier = Modifier.height(40.dp)) {
        Box(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
        ) {

            Canvas(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            ) {

                drawIntoCanvas { canvas ->
                    val paint = Paint()
                    paint.strokeWidth = 3f
//                    paint.shader =
//                        getGradientShader(listColor, listStop)
                    paint.style = PaintingStyle.Fill
                    var rect = Rect(
                        0f, 0f, width.toFloat(), 10 * density
                    )
                    canvas.drawRect(rect, paint)

//                canvas.drawCircle(Offset(50f,50f),50f,paint)
                }
            }
        }

        Image(
            contentDescription = "",
            painter = painterResource(id = iconRes),
            modifier = Modifier
                .offset(
                    (offsetX.value / SCREEN_DENSITY).dp,
                    0.dp
                )
                .size(40.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        if (offsetX.value + dragAmount.x <= width - iconSize && offsetX.value + dragAmount.x >= 0) {
                            offsetX.value += dragAmount.x
//                            Log.e("progress", "offset:" + offsetX.value)
                            var current = (offsetX.value * gross) / (width - iconSize)
//                            Log.e("progress ", "offsetX.value * gross:" + offsetX.value * gross)
//                            Log.e("progress ", "current:" + current)
                            callback(current.toInt() + minValue)
                        }

                    }
                }
        )
    }

}