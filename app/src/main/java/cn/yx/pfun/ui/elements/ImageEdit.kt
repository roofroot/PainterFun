package cn.yx.pfun.ui.elements

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.onFocusedBoundsChanged
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cn.yx.pfun.R
import cn.yx.pfun.SCREEN_DENSITY
import cn.yx.pfun.data.MyPageImageEntity
import cn.yx.pfun.data.Point
import cn.yx.pfun.data.TipsEntity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.itextpdf.text.Image
import java.util.Collections.rotate
import kotlin.math.roundToInt

lateinit var toolsRotate: MutableState<Float>

@Composable
fun getImage(entity: MyPageImageEntity) {
    var stateAction = remember {
        mutableStateOf(false)
    }
    entity.offsetXFrame = remember {
        mutableStateOf(entity.offsetXFrame.value)
    }
    entity.offsetYFrame = remember {
        mutableStateOf(entity.offsetYFrame.value)
    }
    entity.offsetX = remember {
        mutableStateOf(entity.offsetX.value)
    }
    entity.offsetY = remember {
        mutableStateOf(entity.offsetY.value)
    }
    entity.imageW = remember {
        mutableStateOf(entity.imageW.value)
    }
    entity.imageH = remember {
        mutableStateOf(entity.imageH.value)
    }
    entity.imageRotate = remember {
        mutableStateOf(entity.imageRotate.value)
    }
    toolsRotate = remember {
        mutableStateOf(entity.imageRotate.value)
    }
    Box(
        Modifier
            .wrapContentHeight()
            .width(Dp(entity.imageW.value) + 50.dp)
            .offset {
                IntOffset(
                    entity.offsetXFrame.value.roundToInt(),
                    entity.offsetYFrame.value.roundToInt()
                )
            }
            .pointerInput(Unit) {
                detectDragGestures({
                }, {
                }, {

                }, { change, dragAmount ->
                    change.consumeAllChanges()

//                        if(0<offsetXFrame.value+dragAmount.x) {
                    entity.offsetXFrame.value += dragAmount.x
                    entity.offsetX.value += dragAmount.x
//                        }
//                        if(offsetYFrame.value+dragAmount.y>0){
                    entity.offsetYFrame.value += dragAmount.y
                    entity.offsetY.value += dragAmount.y
//                        }
                })


            },
    ) {

        Box(

        ) {
//            if (entity.isRotate.value) {
//
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(entity.imagePath)
//                        .allowHardware(false)
//                        .crossfade(true)
//                        .build(),
//                    placeholder = painterResource(R.drawable.baseline_image_24),
//                    contentDescription = "My content description",
//                    contentScale = ContentScale.FillBounds,
//                    modifier =  Modifier
//                        .width(Dp(entity.imageW.value))
//                        .height(Dp(entity.imageH.value))
//                        .rotate(entity.imageRotate.value)
//                        .border(2.dp, color = currentEditThemeColor.colorBg).pointerInput(Unit) {
//                            detectDragGestures({
//
//                            }, {
//                                entity.isRotate.value = false
//                                toolsRotate.value = entity.imageRotate.value
//                            }, {
//                                entity.isRotate.value = false
//                                toolsRotate.value = entity.imageRotate.value
//                            }, { change, dragAmount ->
//                                onRatate(entity, change, dragAmount)
//                            })
//                        },
//                    onLoading = {
//
//                    })
//            }
//            else{
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entity.imagePath)
                    .allowHardware(false)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.baseline_image_24),
                contentDescription = "My content description",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(Dp(entity.imageW.value))
                    .height(Dp(entity.imageH.value))
                    .rotate(entity.imageRotate.value)
                    .align(Alignment.BottomCenter)
                    .border(2.dp, color = currentEditThemeColor.colorBg),
                onLoading = {

                })
//            }
            Box(
                Modifier
                    .width(Dp(entity.imageW.value))
                    .height(Dp(entity.imageH.value))
                    .rotate(entity.imageRotate.value)
            ) {
                if (entity.isRotate.value) {
                    Box(
                        Modifier
                            .align(Alignment.TopCenter)
                            .background(Color.Blue)
                            .size(size = 50.dp)
                    ) {

                    }
                }
                ActionWeight(
                    entity = entity,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .wrapContentHeight()
                        .wrapContentWidth()
                )
            }


            if (entity.isRotate.value) {
                Box(
                    Modifier
                        .align(Alignment.TopStart)
                        .background(Color.Blue)
                        .size(size = 50.dp)
                        .pointerInput(Unit) {
                            detectDragGestures({
                                isShowTips.value = false
                                tipList.clear()
                            }, {
                                entity.isRotate.value = false
                                toolsRotate.value = entity.imageRotate.value
                            }, {
                                entity.isRotate.value = false
                                toolsRotate.value = entity.imageRotate.value
                            }, { change, dragAmount ->
                                onRatate(entity, change, dragAmount)
                            })
                        }) {

                }
            }


        }

    }


}