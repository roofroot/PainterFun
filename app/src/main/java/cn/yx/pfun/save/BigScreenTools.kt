package cn.yx.pfun.save

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.View
import cn.open.book.application.MyApplication
import cn.yx.pfun.tools.ScreenTools.bitmaps2
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

object BigScreenTools {
    /**
     * 截取scrollview的屏幕
     *
     * @param scrollView
     * @return
     */
    var bitmaps = ArrayList<String>()

    //    var bitmaps2 = ArrayList<Bitmap>()
    var aaa = ""
    var longSize = 1;
    var aBoolean = true
    val fileDir = MyApplication.instance.getExternalFilesDir("Pictures")

    @Synchronized
    fun getBitmapByView(scrollView: View, height: Int, width: Int) {
        try {
            var height = height
            var width = width
            Log.e(
                "ScreenShot",
                "start get bitmap offsetX" + scrollView.x + "offsetY" + scrollView.y
            )
            var bitmap = Bitmap.createBitmap(
                scrollView.width, scrollView.height,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            scrollView.draw(canvas)
            if (height > 0 && height < scrollView.height || width > 0 && width < scrollView.width) {
                if (width <= 0) {
                    width = scrollView.width
                }
                if (height <= 0) {
                    height = scrollView.height
                }
                bitmap = Bitmap.createBitmap(
                    bitmap,
                    scrollView.width - width,
                    scrollView.height - height,
                    width,
                    height
                )
            }
            bitmaps.add(saveImage(MyApplication.instance, bitmap = bitmap))
            Log.e("ScreenShot", "finish get bitmap")
        } catch (e: Exception) {
            Log.e("ScreenShot", "error get bitmap" + e.message)
        }
    }


    fun getPercent(h: Float, w: Float): Int {
        var p = 0
        var p2 = 0.0f
        p2 = if (h > w) {
            (594 + 120) / h * 100
        } else {
            (320 + 64) / w * 100
        }
        p = Math.round(p2)
        return p
    }


    fun saveImage(context: Context, bitmap: Bitmap): String {
        // 测试输出
        var out: FileOutputStream? = null
        // 保存图片

        aaa = "开始保存小图片"
        Log.e("ScreenShot", aaa)
        var a = Canvas(bitmap)
        var p = Paint()
        p.color = Color.RED
        p.textSize = 100f
        a.drawText("num" + bitmaps.size, 100f, 100f, p)
        val path =
            fileDir!!.absolutePath + "/" + System.currentTimeMillis() + bitmaps.size + "small_screenshot.jpg"
        try {
            out =
                FileOutputStream(path)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
                out.close()
                bitmap.recycle()
                aaa = "保存小图片成功"
                Log.e("ScreenShot", aaa)
            }
            return path
        } catch (e: IOException) {
            // TODO: handle exception
            aaa = "保存小图失败"
            Log.e("ScreenShot", aaa)
        }
        return "";
    }


    @Synchronized
    fun saveBigImage(context: Context, width: Int, height: Int, viewW: Int) {
        val bitmap = mergeBig(width, height, viewW)
        // 测试输出
        var out: FileOutputStream? = null
        // 保存图片
        bitmaps.clear()
        val fileDir = context.getExternalFilesDir("Pictures")
        aaa = "开始生成图片 width：" + width + " height：" + height + "view W" + viewW
        Log.e("ScreenShot", aaa)
        try {
            out =
                FileOutputStream(fileDir!!.absolutePath + "/" + System.currentTimeMillis() + "screenshot.jpg")
            aaa = "获取目录成功"
            Log.e("ScreenShot", aaa)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            aaa = "获取目录失败"
            Log.e("ScreenShot", aaa)
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
                out.close()
                bitmaps.clear()
                bitmap.recycle()
                aaa = "生成图片成功"
                longSize = 1
                Log.e("ScreenShot", aaa)
            }
        } catch (e: IOException) {
            // TODO: handle exception
            aaa = "生成图片失败"
            Log.e("ScreenShot", aaa)
        }
    }


    fun mergeBig(width: Int, height: Int, viewW: Int): Bitmap {
        aaa = "开始merge"
        Log.e("ScreenShot", aaa)
        val bigbitmap =
            Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        aaa = "创建完成"
        Log.e("ScreenShot", aaa)
        val bigcanvas = Canvas(bigbitmap)

        aaa = "cavas 创建完成"
        Log.e("ScreenShot", aaa)
        //        Comparable(bitmaps.get(bitmaps.size() - 2),
//                bitmaps.get(bitmaps.size() - 1));
        val paint = Paint()
        var iHeight = 0
        var iWidth = -viewW
        aaa = "循环开始"
        Log.e("ScreenShot", "longsize ：" + longSize + "bitmaplist.size" + bitmaps.size)
        Log.e("ScreenShot", aaa)
        for (i in bitmaps.indices) {
            var bmp: Bitmap = BitmapFactory.decodeFile(bitmaps[i])
            if (i % longSize == 0) {
                iHeight = 0
                iWidth += bmp.width
            }
            Log.e("ScreenShot", "iHeight:" + iHeight + "iWidth:" + iWidth)
            bigcanvas.drawBitmap(bmp, iWidth.toFloat(), iHeight.toFloat(), paint)
            iHeight += bmp.height
            bmp.recycle()
        }
        aaa = "循环结束"
        Log.e("ScreenShot", aaa)
        return bigbitmap
    }


    fun split(bitmap: Bitmap) {
        val paint = Paint()
        var iHeight = 0
        var i = 0
        while (true) {
            val bmp =
                Bitmap.createBitmap(bitmap.width, bitmap.width * 842 / 595, Bitmap.Config.ARGB_8888)
            val bmpCanvas = Canvas(bmp)
            //            bmpCanvas.drawBitmap(bitmap, 0, 0, paint);
            bmpCanvas.drawBitmap(
                bitmap,
                Rect(0, iHeight, bmp.width, iHeight + bmp.height),
                Rect(0, 0, bmp.width, bmp.height),
                paint
            )
            bitmaps2.add(bmp)
            iHeight += bmp.height
            if (iHeight > bitmap.height) {
                break
            }
            i++
        }
    }
}