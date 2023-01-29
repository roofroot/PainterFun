package cn.yx.pfun.tools

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import cn.yx.pfun.save.BigScreenTools
import cn.yx.pfun.ui.elements.scrollX
import cn.yx.pfun.ui.elements.scrollY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScreenShotBigCopy() {
    var offsetY = 0f
    var offsetX = 0f
    val scope = CoroutineScope(Job() + Dispatchers.IO)
    val mscope = CoroutineScope(Job() + Dispatchers.Main)


    lateinit var view: View
    lateinit var context: Context
    var picHeight = 0
    var picWidth = 0
    val TAG = "ScreenShotBig"

    fun init(context: Context, view: View) {
        this.context = context
        this.view = view

    }

    fun setPicSize(w: Int, h: Int) {
        this.picHeight = h
        this.picWidth = w
    }

    companion object {
        @Volatile
        private var manager: ScreenShotBigCopy? = null
        fun getInstance() =
            manager ?: synchronized(this) {
                manager ?: ScreenShotBigCopy().also { manager = it }
            }

    }

    @Synchronized
    suspend fun getBitmap(onCompletion: () -> Unit) {

        BigScreenTools.getBitmapByView(
            view, floatY.toInt(), floatX.toInt()
        );
        offsetY += view.height

//        floatY = scrollY.scrollTo()
        scope.launch {
            floatY = scrollY.scrollTo(offsetY.toInt())
        }.invokeOnCompletion {
            onCompletion()
        }

    }

    suspend fun saveBitmap(): Boolean = withContext(Dispatchers.IO) {
        try {
            BigScreenTools.saveBigImage(context, picWidth, picHeight, view.width)
        } catch (e: Exception) {
            false
        }
        true
    }

    private var bool = true
    var time = -1L;
    fun doAction(): Boolean {
        if (bool && System.currentTimeMillis() - time > 5000) {
            bool = false
            scope.launch {
                floatY = scrollY.scrollTo(0)
                floatX = scrollX.scrollTo(0)
                floatX = 0f
                floatY = 0f
                offsetX = 0f
                offsetY = 0f
            }.invokeOnCompletion {
                if (floatX == 0f && floatY == 0f) {
                    scope.launch {
                        action()
                    }
                }
            }
        }
        return false;
    }

    var floatY = 0f
    var floatX = 0f
    suspend fun action() {

        getBitmap {
            scope.launch {
                Log.e(TAG, "获取图片后 floatY ：" + floatY)
                Log.e(TAG, "获取图片后 offsetY ：" + offsetY)
                if (floatY == 0f) {
                    if (BigScreenTools.longSize == 1) {
                        BigScreenTools.longSize = (offsetY / view.height).toInt()
                        Log.e(TAG, "longsize ：" + BigScreenTools.longSize)
                    }
                    Log.e(TAG, "滚动到了底部 floatY：" + floatY)
                    offsetX += view.width
                    floatX = scrollX.scrollTo(offsetX.toInt())
                    Log.e(
                        TAG,
                        "尝试横向滚动 floatX：" + floatX + "offsetX:" + offsetX + "view.width" + view.width
                    )
                    Log.e(TAG, "尝试纵向滚动 floatX：" + floatX)
                    if (floatX > 0) {
                        floatY = scrollY.scrollTo(0)
                        Log.e(TAG, "纵向滚动到0 floatY：" + floatY)
                        offsetY = 0f
                        Log.e(TAG, "重置floatY 重置offsetY floatY：" + floatY)
                    }
                }

                if (floatY == 0f && floatX == 0f) {
                    Log.e(TAG, "纵向与横向均无法滚动 floatX：" + floatX)
                    Log.e(TAG, "纵向与横向均无法滚动 floatY：" + floatY)
                    var boolean = saveBitmap()
                    ScreenTools.bitmaps.clear()
                    ScreenTools.bitmaps2.clear()
                    offsetY = 0f;
                    offsetX = 0f;
                    time = System.currentTimeMillis()
                    mscope.launch {
                        Toast.makeText(context, ScreenTools.aaa, Toast.LENGTH_SHORT).show()
                    }
                    bool = true
                } else {
                    action()
                }
            }
        }

    }

}