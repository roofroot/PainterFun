package cn.yx.pfun.ui.elements

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cn.yx.pfun.R
import cn.yx.pfun.SCREEN_DENSITY
import cn.yx.pfun.SCREEN_H
import cn.yx.pfun.SCREEN_W
import cn.yx.pfun.data.MyPageImageEntity
import cn.yx.pfun.data.MyPageTextEntity
import cn.yx.pfun.data.Point
import cn.yx.pfun.data.TipsEntity
import cn.yx.pfun.tools.fingerAngleCenter
import cn.yx.pfun.tools.fingerArcbyCenter
import cn.yx.pfun.ui.theme.plum
import cn.yx.pfun.ui.theme.thistle
import kotlin.math.roundToInt


lateinit var ra: MutableState<Float>
lateinit var rb: MutableState<Float>
lateinit var rc: MutableState<Float>
lateinit var rd: MutableState<Float>
lateinit var actionBtnBg: MutableState<Color>

@Composable
fun ActionWeight(entity: MyPageImageEntity, modifier: Modifier) {
    val left: Float = 0f;
    val right: Float = 30f;
    val top: Float = 0f;
    val bottom: Float = 30f;
    var center: Float;
    var isActionBarVisibility = remember {
        mutableStateOf(false)
    }
    actionBtnBg = remember {
        mutableStateOf(plum)
    }
    entity.isRotate = remember {
        mutableStateOf(false)
    }


    ra = remember {
        mutableStateOf(0f)
    }
    rb = remember {
        mutableStateOf(0f)
    }

    rc = remember {
        mutableStateOf(0f)
    }
    rd = remember {
        mutableStateOf(0f)
    }


    val btnSize = Dp(50f)
    Row(
        modifier
            .padding(end = btnSize)
            .background(currentEditThemeColor.colorFg)
    ) {
        if (isActionBarVisibility.value) {
            Text("上一层", modifier = Modifier
                .size(50.dp)
                .clickable {
//                    var index = editPageData.indexOf(entity)
//                    var temp: EditPageElement?
//                    if (index < editPageData.size - 1) {
//                        temp = editPageData[index + 1]
//                        editPageData[index + 1] = entity
//                        editPageData[index] = temp
//                        navigation.refreshState(Navigation.MYPAGESTATE)
//                    }
                    isActionBarVisibility.value = false

                })
            Text("下一层", modifier = Modifier
                .size(50.dp)
                .clickable {
//                    var index = editPageData.indexOf(entity)
//                    var temp: EditPageElement?
//                    if (index > 0) {
//                        temp = editPageData[index - 1]
//                        editPageData[index - 1] = entity
//                        editPageData[index] = temp
//                        navigation.refreshState(Navigation.MYPAGESTATE)
//                    }
                    isActionBarVisibility.value = false

                })

            Text("旋转", modifier = Modifier
                .size(50.dp)
                .clickable {
                    isActionBarVisibility.value = false
                    entity.isRotate.value = true
//                    entity.imageRotate.value=315f
                    tipList.add(
                        TipsEntity(
                            "按住此处，拖拽旋转", true,
                            Point(
                                entity.offsetXFrame.value + 25* SCREEN_DENSITY,
                                entity.offsetYFrame.value + 25* SCREEN_DENSITY
                            ),
                            Point(
                                entity.offsetXFrame.value - 100* SCREEN_DENSITY,
                                entity.offsetYFrame.value - 50* SCREEN_DENSITY
                            ),
                            currentEditThemeColor
                        )
                    )
                    isShowTips.value = true
                    actionBtnBg.value = Color.Transparent
                    if (entity.isRotate.value) {
                        ra.value =
                            (entity.offsetX.value - entity.offsetXFrame.value) / 2
                        rb.value =
                            (entity.offsetY.value - entity.offsetYFrame.value) / 2
                        var height = entity.imageH.value * SCREEN_DENSITY
                        var width = entity.imageW.value * SCREEN_DENSITY

                    }
                })
        }


    }

    Canvas(
        modifier
            .background(actionBtnBg.value)
            .size(size = btnSize)
            .pointerInput(Unit) {
                detectDragGestures({
                    actionBtnBg.value = thistle
                }, {
                    actionBtnBg.value = thistle
                }, {
                    actionBtnBg.value = thistle
                }, { change, dragAmount ->

                    onResize(entity, change, dragAmount)

                })
            }
            .clickable {
                isActionBarVisibility.value = !isActionBarVisibility.value
            }
    )
    {

        drawIntoCanvas { canvas ->
            val paint = Paint()
            paint.color = Color.Black
            paint.strokeWidth = 3f
            paint.style = PaintingStyle.Stroke
            var rect = Rect(
                left, top, right, bottom
            )
            canvas.drawRect(rect, paint)
//                canvas.drawCircle(Offset(50f,50f),50f,paint)
        }
    }

}

fun onResize(entity: MyPageImageEntity, change: PointerInputChange, dragAmount: Offset) {
    if (dragAmount.x > 0 && dragAmount.y > 0 && entity.offsetX.value - entity.offsetXFrame.value < panelSize.w
        && entity.imageH.value < panelSize.h
    ) {
        entity.imageW.value += ((dragAmount.x / SCREEN_DENSITY))
        entity.imageH.value = entity.imageW.value * entity.bl
        entity.offsetY.value =
            entity.offsetYFrame.value + entity.imageH.value * SCREEN_DENSITY
        entity.offsetX.value =
            entity.offsetXFrame.value + entity.imageW.value * SCREEN_DENSITY
    }
//
    if (dragAmount.x < 0 && dragAmount.y < 0 && entity.imageW.value + dragAmount.x / SCREEN_DENSITY > 40) {
        entity.imageW.value += ((dragAmount.x / SCREEN_DENSITY))
        entity.imageH.value = entity.imageW.value * entity.bl

        entity.offsetY.value =
            entity.offsetYFrame.value + entity.imageH.value * SCREEN_DENSITY
        entity.offsetX.value =
            entity.offsetXFrame.value + entity.imageW.value * SCREEN_DENSITY
    }
}


fun onRatate(entity: MyPageImageEntity, change: PointerInputChange, dragAmount: Offset) {

    ra.value =
        (entity.offsetX.value - entity.offsetXFrame.value) / 2
    rb.value =
        (entity.offsetY.value - entity.offsetYFrame.value) / 2
    var height = entity.imageH.value * SCREEN_DENSITY
    var width = entity.imageW.value * SCREEN_DENSITY

    //x1 = x0 + r * cos(angel * PI /180 )  求圆上点
    // y1 = y0 + r * sin(angel * PI /180 )
    // angel * PI /180  角度转弧度

    //半径
    var r = Math
        .sqrt((width / 2 * width / 2 + height / 2 * height / 2).toDouble())
        .toFloat()
    //周长
    var c = 2 * Math.PI * r

    var l = Math.sqrt((r * r - width / 2 * width / 2).toDouble())
    var offsetAngle = Math.acos(l / r) * 180 / Math.PI//右下脚与水平偏差角度

    var fingerAngleCenter = fingerAngleCenter(
        Point(change.position.x, change.position.y),
        Point(ra.value, rb.value)
    )

    var fingerArcbyCenter =
        fingerArcbyCenter(
            Point(change.position.x, change.position.y),
            Point(ra.value, rb.value)
        )

    var rotateRandians = fingerArcbyCenter//弧度
    var angle = rotateRandians * 90 / Math.PI//角度
    var arc = rotateRandians * r//弧长
    Log.e(
        "rotate", "${rc.value}， ${rd.value} " +
                ",rotate 弧度:${rotateRandians} 角度:{$angle} 弧长:{$arc} " +
                "手指偏差角：${fingerAngleCenter} 手指偏差弧${
                    fingerArcbyCenter
                }"
    )

    if (0 < fingerAngleCenter.toFloat() && fingerAngleCenter <= 90) {
        fingerAngleCenter = 90 - fingerAngleCenter
    } else if (90 < fingerAngleCenter && fingerAngleCenter < 180) {
        fingerAngleCenter = 270 + 180 - fingerAngleCenter
    } else if (180 < fingerAngleCenter && fingerAngleCenter < 270) {
        fingerAngleCenter = 180 + 270 - fingerAngleCenter
    } else {
        fingerAngleCenter = 90 + 360 - fingerAngleCenter
    }

    entity.imageRotate.value = fingerAngleCenter.toFloat()

    Log.e(
        "rotate", "${rc.value}， ${rd.value} " +
                ",rotate 弧度:${rotateRandians} 角度:{$angle} 弧长:{$arc} " +
                "手指偏差角：${fingerAngleCenter} 手指偏差弧${
                    fingerArcbyCenter
                }"
    )

}

@Composable
fun ActionWeight(entity: MyPageTextEntity) {

    val left: Float = 0f;
    val right: Float = 30f;
    val top: Float = 0f;
    val bottom: Float = 30f;
    var isActionBarVisibility = remember {
        mutableStateOf(false)
    }
    var actionBtnBg = remember {
        mutableStateOf(Color(0x0f02f78e))
    }
    if (isActionBarVisibility.value) {
        Column(Modifier
            .offset {
                IntOffset(
                    entity.offsetX.value.roundToInt(),
                    entity.offsetY.value.roundToInt() - (SCREEN_DENSITY * 100).toInt()
                )
            }
            .width(50.dp)
            .wrapContentHeight()) {
            Text("上一层", modifier = Modifier
                .size(50.dp)
                .clickable {
//                    var index = editPageData.indexOf(entity)
//                    var temp: EditPageElement?
//                    if (index < editPageData.size - 1) {
//                        temp = editPageData[index + 1]
//                        editPageData[index + 1] = entity
//                        editPageData[index] = temp
//                        navigation.refreshState(Navigation.MYPAGESTATE)
//                    }
                    isActionBarVisibility.value = false

                })
            Text("下一层", modifier = Modifier
                .size(50.dp)
                .clickable {
//                    var index = editPageData.indexOf(entity)
//                    var temp: EditPageElement?
//                    if (index > 0) {
//                        temp = editPageData[index - 1]
//                        editPageData[index - 1] = entity
//                        editPageData[index] = temp
//                        navigation.refreshState(Navigation.MYPAGESTATE)
//                    }
                    isActionBarVisibility.value = false

                })

        }
    }
    Canvas(
        Modifier
            .offset {
                IntOffset(
                    entity.offsetX.value.roundToInt(),
                    entity.offsetY.value.roundToInt()
                )
            }
            .background(actionBtnBg.value)
            .size(Dp(50f))
            .pointerInput(Unit) {
//                detectDragGestures { change, dragAmount ->
//
//                }
                detectDragGestures({
                    actionBtnBg.value = Color(0xff02f78e)
                }, {
                    actionBtnBg.value = Color(0x0f02f78e)
                }, {
                    actionBtnBg.value = Color(0x0f02f78e)
                }, { change, dragAmount ->

                    change.consumeAllChanges()
                    if (entity.offsetX.value + dragAmount.x - entity.offsetXFrame.value > 40
                        && entity.offsetX.value + dragAmount.x - entity.offsetXFrame.value < SCREEN_W
                        && entity.offsetY.value - entity.offsetYFrame.value < SCREEN_H - 80 * SCREEN_DENSITY
                    ) {
                        entity.offsetX.value += dragAmount.x
                    }
                    if (entity.offsetY.value + dragAmount.y - entity.offsetYFrame.value > 20
                        && entity.offsetY.value + dragAmount.y - entity.offsetYFrame.value < SCREEN_H
                    ) {
                        entity.offsetY.value += dragAmount.y
                    }

                })
            }
            .clickable {
                isActionBarVisibility.value = !isActionBarVisibility.value
            }
    )
    {

        drawIntoCanvas { canvas ->
            val paint = Paint()
            paint.color = Color.Black
            paint.strokeWidth = 3f
            paint.style = PaintingStyle.Stroke
            var rect = Rect(
                left, top, right, bottom
            )
            canvas.drawRect(rect, paint)
//                canvas.drawCircle(Offset(50f,50f),50f,paint)
        }
    }

}