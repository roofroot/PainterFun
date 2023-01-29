package cn.yx.pfun.tools

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import cn.yx.pfun.ui.elements.scrollY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScreenShotLong() {
    var offsetY = 0f
    val scope = CoroutineScope(Job() + Dispatchers.IO)
    val mscope = CoroutineScope(Job() + Dispatchers.Main)
    lateinit var view: View
    lateinit var context: Context

    fun init(context: Context, view: View) {
        this.context = context
        this.view = view
    }

    companion object {
        @Volatile
        private var manager: ScreenShotLong? = null
        fun getInstance() =
            manager ?: synchronized(this) {
                manager ?: ScreenShotLong().also { manager = it }
            }

    }

    @Synchronized
    suspend fun getBitmap(): Float {
        ScreenTools.getBitmapByView(
            view, float.toInt()
        );
        offsetY += view.height
        return scrollY.scrollTo(offsetY.toInt())
    }

    suspend fun saveBitmap(): Boolean = withContext(Dispatchers.IO) {
        try {
//            ScreenTools.split(ScreenTools.merge());
//            ScreenTools.savePdf(context)
            ScreenTools.saveLongImage(context);

        } catch (e: Exception) {
            false
        }
        true
    }

    private var bool = true
    var time = -1L;
    fun doAction(): Boolean {
        if (bool && System.currentTimeMillis() - time > 5000) {
            Log.e("errer", "start")
            bool = false
            scope.launch {
                float = scrollY.scrollTo(0)
                float = 0f
            }.invokeOnCompletion {
                if (float == 0f) {
                    scope.launch {
                        action()
                    }
                }

            }
        }
        return false;
    }

    var float = 0f
    suspend fun action() {
        float = getBitmap()
        if (float == 0f) {
            var boolean = saveBitmap()
            ScreenTools.bitmaps.clear()
            ScreenTools.bitmaps2.clear()
            offsetY = 0f;
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