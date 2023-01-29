package cn.yx.pfun.ui.elements

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cn.yx.pfun.SCREEN_DENSITY
import cn.yx.pfun.SCREEN_H
import cn.yx.pfun.SCREEN_W
import cn.yx.pfun.data.EditPageElement
import cn.yx.pfun.data.MyPageEntity
import cn.yx.pfun.data.MyPageImageEntity
import cn.yx.pfun.data.PanelSize
import cn.yx.pfun.data.TipsEntity
import cn.yx.pfun.ui.theme.azure
import cn.yx.pfun.ui.theme.gainsboro


@Composable
fun panelRoot() {
    scrollY = rememberScrollState(0)
    scrollX = rememberScrollState(0)
    editSplash = remember {
        mutableStateOf(false)
    }
    panelView()
}

lateinit var boxHeight: MutableState<Dp>
lateinit var boxWidth: MutableState<Dp>
val minWidth = (SCREEN_W / SCREEN_DENSITY).dp
val minHeight = (SCREEN_H / SCREEN_DENSITY).dp
val minPxW = SCREEN_W.toFloat()
val minPxH = SCREEN_H.toFloat()
val panelSize = PanelSize(minPxW, 3000f)
var oldEditPageSize = editPageData.size

@Composable
fun panelView() {


    boxHeight = remember {
        mutableStateOf((panelSize.h / SCREEN_DENSITY).dp)
    }
    boxWidth = remember {
        mutableStateOf((panelSize.w / SCREEN_DENSITY).dp)
    }
    isShowTips = remember {
        mutableStateOf(false)
    }

    Box(
        Modifier
            .verticalScroll(scrollY)
            .horizontalScroll(scrollX)
            .border(5.dp, color = list.get(selectedPos.value).pairColors.colorBg)
            .height(boxHeight.value)
            .width(boxWidth.value)
    ) {
        panelBg(
        )
        Log.e("aaa", "edit splash" + editSplash.value + "," + oldEditPageSize)
        if (editSplash.value) {
            addToPanelView()
        }else{
            addToPanelView()
        }
        if(isShowTips.value){
            tipsViewRoot(list = tipList)
        }
//            Box(
//                Modifier
//                    .background(color = list.get(selectedPos.value).pairColors.colorFg)
//                    .fillMaxWidth()
//                    .height(boxHeight.value)
//            ){
//
//            }

    }


}

@Composable
fun addToPanelView() {
    Log.e("aaa", "change")
    editPageData.forEach { editPageElement ->
//        getImageElement(entity = editPageElement as MyPageImageEntity)
        getImage(entity = editPageElement as MyPageImageEntity)
    }
    oldEditPageSize = editPageData.size
}

fun setPanelSize(size: PanelSize) {
    var w = 0f
    var h = 0f
    if (size.w > size.h) {
        h = minPxH
        w = size.w / size.h * h
        boxHeight.value = minHeight
        boxWidth.value = (w / SCREEN_DENSITY).dp
    } else {
        w = minPxW
        h = size.h / size.w * w
        boxWidth.value = minWidth
        boxHeight.value = (h / SCREEN_DENSITY).dp
    }
}

@Composable
fun panelBg() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(gainsboro)
    ) {
        this.size
        var squareWidth = 50f
        var heightEnd = 0f
        var widthEnd = 0f
        val paint = Paint()
        paint.style = PaintingStyle.Fill
        var row = 0
        var count = 0
        while (heightEnd < this.size.height) {
            var a = row % 2
            while (widthEnd < this.size.width) {
                if (count % 2 == a) {
                    this.drawRect(
                        color = azure,
                        topLeft = Offset(widthEnd, heightEnd),
                        size = Size(squareWidth, squareWidth)
                    )
                }
                widthEnd += squareWidth
                count++
            }
            widthEnd = 0f
            heightEnd += squareWidth
            row++
        }
    }
}
