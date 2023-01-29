package cn.yx.pfun.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import java.text.FieldPosition

class PairColors(val colorFg: Color, val colorBg: Color)
class TagData(val title: String, var pairColors: PairColors, var height: MutableState<Dp>)
class PageData()
data class PanelSize(var w: Float, var h: Float)
data class Point(var x: Float, var y: Float)
data class MyPageEntity(
    var elements: ArrayList<EditPageElement> = arrayListOf()
)

open class EditPageElement(
    var offsetXFrame: MutableState<Float> = mutableStateOf(0f),
    var offsetYFrame: MutableState<Float> = mutableStateOf(0f),
    var offsetX: MutableState<Float> = mutableStateOf(120f),
    var offsetY: MutableState<Float> = mutableStateOf(90f),
    var index: Int = -1,
    var isRotate: MutableState<Boolean> = mutableStateOf(false),
)

data class MyPageImageEntity(
    var imageW: MutableState<Float> = mutableStateOf(40f),
    var imageH: MutableState<Float> = mutableStateOf(30f),
    var imagePath: String = "",
    var imageMaxW: Float = 0f,
    var imageRotate: MutableState<Float> = mutableStateOf(0f),
    var bl: Float = 1f,
) : EditPageElement()


data class MyPageTextEntity(
    var textContent: String = "",
    var textColor: Color = Color.Black,
    var bgColor: Color = Color.Transparent
) : EditPageElement()

data class TipsEntity(var name: String, var isShow: Boolean, var lineStart: Point,var lineEnd: Point,var colors:PairColors)