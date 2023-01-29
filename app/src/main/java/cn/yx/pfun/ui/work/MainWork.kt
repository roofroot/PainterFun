package cn.yx.pfun.ui.work

import android.app.Activity
import android.util.Log
import android.view.View
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

import cn.yx.pfun.MyBaseWork
import cn.yx.pfun.SCREEN_DENSITY
import cn.yx.pfun.SCREEN_H
import cn.yx.pfun.SCREEN_W
import cn.yx.pfun.data.MyPageImageEntity
import cn.yx.pfun.data.TagData
import cn.yx.pfun.databinding.ActivityMainBinding
import cn.yx.pfun.tools.ActivityManager
import cn.yx.pfun.save.ScreenShotBig
import cn.yx.pfun.tools.ScreenShotLong
import cn.yx.pfun.ui.elements.bottomBar
import cn.yx.pfun.ui.elements.currentEditThemeColor
import cn.yx.pfun.ui.elements.editPageData
import cn.yx.pfun.ui.elements.editSplash
import cn.yx.pfun.ui.elements.list
import cn.yx.pfun.ui.elements.panelBg
import cn.yx.pfun.ui.elements.panelRoot
import cn.yx.pfun.ui.elements.resizePanel
import cn.yx.pfun.ui.elements.selectPicResult
import cn.yx.pfun.ui.elements.selectedPos
import cn.yx.pfun.widget.getRandomColor
import cn.yx.pfun.widget.tabsBar
import com.media.manager.SelectManager


/**
 * yuxiu
 * 2022/10/4
 **/


class MainWork(binding: ActivityMainBinding, context: Activity) :
    MyBaseWork<ActivityMainBinding>(binding, context) {

    init {
        list = buildList<TagData> {
            for (i in 1..10) {
                add(
                    TagData(
                        "标题${i}", getRandomColor(),
                        mutableStateOf(50.dp)
                    )
                )
            }
        }
        selectedPos =
            mutableStateOf(0)
        currentEditThemeColor = list[0].pairColors


        binding.top.setContent {
            list = buildList<TagData> {
                for (i in 1..10) {
                    add(TagData("标题${i}", getRandomColor(), remember {
                        mutableStateOf(50.dp)
                    }))
                }
            }
            selectedPos = remember {
                mutableStateOf(0)
            }
            tabsBar(list = list)
            resizePanel()
        }
        binding.panel.setContent {
            panelRoot()
        }

        binding.back.setContent {
            panelBg()
        }
        binding.bottom.setContent {
            bottomBar()
        }

        ScreenShotLong.getInstance().init(context, binding.panel)
        ScreenShotBig.getInstance().init(context, binding.panel)
        ActivityManager.getInstance().init(context = context)
        SelectManager.getInstance().onFinish = {
            Log.e("aaa", "finish")
            selectPicResult?.value = 0
            SelectManager.getSelectList().map {
                var entity = MyPageImageEntity()
                var imageItem = it.value
                entity.imagePath = imageItem.path
                if (imageItem.height > SCREEN_H - 80 * SCREEN_DENSITY && imageItem.width > SCREEN_W) {
                    var bl: Float = (imageItem.height.toFloat() / imageItem.width.toFloat())
                    var width: Float = (SCREEN_W).toFloat()
                    var height: Float = width * bl
                    entity.bl = bl
                    entity.imageH = mutableStateOf(height / SCREEN_DENSITY)
                    entity.imageW = mutableStateOf(width / SCREEN_DENSITY)
                    entity.imageMaxW = width / SCREEN_DENSITY
                    entity.offsetX = mutableStateOf(width)
                    entity.offsetY = mutableStateOf(height)

                } else if (imageItem.height > SCREEN_H) {
                    var height: Float = (SCREEN_H - 80 * SCREEN_DENSITY).toFloat()
                    var bl: Float = (imageItem.height.toFloat() / imageItem.width.toFloat())
                    var width: Float = height.toFloat() / bl
                    entity.bl = bl
                    entity.imageH = mutableStateOf(height / SCREEN_DENSITY)
                    entity.imageW = mutableStateOf(width / SCREEN_DENSITY)
                    entity.imageMaxW = width / SCREEN_DENSITY
                    entity.offsetX = mutableStateOf(width)
                    entity.offsetY = mutableStateOf(height)
                } else if (imageItem.width > SCREEN_W) {
                    var bl: Float = (imageItem.height.toFloat() / imageItem.width.toFloat())
                    var width: Float = (SCREEN_W).toFloat()
                    var height: Float = width * bl
                    entity.bl = bl
                    entity.imageH = mutableStateOf(height / SCREEN_DENSITY)
                    entity.imageW = mutableStateOf(width / SCREEN_DENSITY)
                    entity.imageMaxW = width / SCREEN_DENSITY
                    entity.offsetX = mutableStateOf(width)
                    entity.offsetY = mutableStateOf(height)
                } else {
                    var height: Float = imageItem.height.toFloat()
                    var bl: Float = (imageItem.height.toFloat() / imageItem.width.toFloat())
                    var width: Float = imageItem.width.toFloat()
                    entity.bl = bl
                    entity.imageH = mutableStateOf(height / SCREEN_DENSITY)
                    entity.imageW = mutableStateOf(width / SCREEN_DENSITY)
                    entity.imageMaxW = width / SCREEN_DENSITY
                    entity.offsetX = mutableStateOf(width)
                    entity.offsetY = mutableStateOf(height)

                }
                editPageData.add(entity)

            }
            SelectManager.getSelectList().clear()
            editSplash.value = !editSplash.value

        }
        SelectManager.getInstance().onClose = {
            selectPicResult?.value = -1
        }

    }

    override fun onClick(view: View?) {

    }
}
