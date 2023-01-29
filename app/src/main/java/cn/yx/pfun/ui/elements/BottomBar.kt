package cn.yx.pfun.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.open.widget_lib.StaggeredGrid
import cn.yx.pfun.R
import cn.yx.pfun.SCREEN_DENSITY
import cn.yx.pfun.SCREEN_W
import cn.yx.pfun.tools.ActivityManager
import cn.yx.pfun.save.ScreenShotBig
import cn.yx.pfun.ui.theme.aliceblue
import cn.yx.pfun.ui.theme.grey
import cn.yx.pfun.ui.theme.lavenderblush
import cn.yx.pfun.ui.theme.lightgoldenrodyellow
import cn.yx.pfun.ui.theme.mintcream
import com.media.activity.MediaSelectActivity

@Composable
fun bottomBar() {
    var height = remember {
        mutableStateOf(20.dp)
    }
    Column(
        Modifier
            .fillMaxWidth()
            .height(height.value)
            .background(Color.White)
            .pointerInput(Unit) {
                detectDragGestures({
                }, {
                }, {
                }, { change, dragAmount ->
                    if (dragAmount.y < 0 && height.value < 300.dp) {
                        height.value -= (dragAmount.y / SCREEN_DENSITY).dp
                    } else if (dragAmount.y > 0 && 20.dp <= height.value) {
                        height.value -= (dragAmount.y / SCREEN_DENSITY).dp
                        if (height.value < 20.dp) {
                            height.value = 20.dp
                        }
                    }
                })

            }) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(color = list.get(selectedPos.value).pairColors.colorBg)
                .clickable {

                }
        ) {

        }
        StaggeredGrid(Modifier.padding(10.dp), SCREEN_W = gridWidth, counts = 6) {
            toolsItems("选择图片", R.drawable.baseline_add_photo_alternate_24,
                bgColor = aliceblue, {
                    ActivityManager.getInstance()
                        .startActivity(MediaSelectActivity::class.java)
                })
            toolsItems("保存", R.drawable.baseline_save_24,
                bgColor = lightgoldenrodyellow, {
//                    ScreenShotLong.getInstance().doAction()
                    ScreenShotBig.getInstance().setPicSize(resizePanelSize.w.toInt(), resizePanelSize.h.toInt())
                    ScreenShotBig.getInstance().doAction()
                })

            toolsItems(
                "编辑区域大小", R.drawable.baseline_settings_overscan_24,
                bgColor = lavenderblush, {
//                    screenShot?.doAction()
                    resizePanelIsExpanded.value = true
                }, fontSize = 14.sp
            )

            toolsItems(
                "添加新窗口", R.drawable.baseline_add_to_queue_24,
                bgColor = mintcream, {
//                    screenShot?.doAction()
                }, fontSize = 15.sp
            )

            toolsItems(
                "添加新窗口", R.drawable.baseline_add_to_queue_24,
                bgColor = mintcream, {
//                    screenShot?.doAction()
                }, fontSize = 15.sp
            )
            toolsItems(
                "添加新窗口", R.drawable.baseline_add_to_queue_24,
                bgColor = mintcream, {
//                    screenShot?.doAction()
                }, fontSize = 15.sp
            )
        }
    }

}

val gridWidth = SCREEN_W - (20 * SCREEN_DENSITY).toInt()

@Composable
fun toolsItems(
    text: String,
    imgRes: Int,
    bgColor: Color,
    onClick: () -> Unit,
    textColor: Color = grey,
    fontSize: TextUnit = 20.sp,
) {
    val size = (gridWidth / (SCREEN_DENSITY * 4) - 0.2).dp
    Column(
        Modifier
            .size(size)
            .background(bgColor)
            .clickable {
                onClick()
            }
    ) {
        Box(
            Modifier
                .weight(0.6f, true)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .size(55.dp)
                    .align(Alignment.BottomCenter),
                painter = painterResource(id = imgRes),
                contentDescription = text,
            )
        }
        Box(
            Modifier
                .weight(0.4f, true)
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = text,
                color = textColor,
                fontSize = fontSize,
            )
        }

    }

}