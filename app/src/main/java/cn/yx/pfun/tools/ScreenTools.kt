package cn.yx.pfun.tools

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.icu.text.ListFormatter.Width
import android.util.Log
import android.view.View
import cn.yx.pfun.SCREEN_H
import cn.yx.pfun.SCREEN_W
import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.Image
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.MalformedURLException

object ScreenTools {
    /**
     * 截取scrollview的屏幕
     *
     * @param scrollView
     * @return
     */

    var bitmaps = ArrayList<Bitmap>()
    var bitmaps2 = ArrayList<Bitmap>()
    var aaa = ""
    var aBoolean = true
    fun getBitmapByView(scrollView: View, height: Int) {
        try {
            Log.e("ScreenShot", "start get bitmap")
            var bitmap = Bitmap.createBitmap(
                scrollView.width, scrollView.height,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            scrollView.draw(canvas)
            if (height > 0 && height < scrollView.height) {
                bitmap = Bitmap.createBitmap(
                    bitmap,
                    0,
                    scrollView.height - height,
                    scrollView.width,
                    height
                )
            }
            bitmaps.add(bitmap)
            Log.e("ScreenShot", "finish get bitmap")
        } catch (e: Exception) {
            Log.e("ScreenShot", "error get bitmap"+e.message)
        }
    }

    fun savePdf(context: Context): Boolean {
        // 测试输出
        var out: FileOutputStream? = null
        // 保存图片
        val fileDir = context.getExternalFilesDir("Pictures")
        return try {
            out =
                FileOutputStream(fileDir!!.absolutePath + "/" + System.currentTimeMillis() + "screenshot.pdf")
            val document = Document()
            PdfWriter.getInstance(document, out)
            document.open()
            for (i in bitmaps2.indices) {
                val stream = ByteArrayOutputStream()
                bitmaps2[i].compress(Bitmap.CompressFormat.PNG, 100, stream)
                val image = Image.getInstance(stream.toByteArray())
                val percent = getPercent(bitmaps2[i].height.toFloat(), bitmaps2[i].width.toFloat())
                image.alignment = Image.MIDDLE
                image.scalePercent(percent.toFloat()) // 表示是原来图像的比例
                document.add(image)
                bitmaps2[i].recycle()
            }
            document.close()
            aaa = "生成文档成功"
            Log.e("sssss", "success")
            true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Log.e("error", e.message!!)
            aaa = " "
            false
        } catch (e: DocumentException) {
            e.printStackTrace()
            Log.e("error", e.message!!)
            aaa = " "
            false
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            Log.e("error", e.message!!)
            aaa = "失败失败失败"
            false
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("error", e.message!!)
            aaa = "失败失败失败"
            false
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

    @Synchronized
    fun saveLongImage(context: Context) {
        val bitmap = merge()
        // 测试输出
        var out: FileOutputStream? = null
        // 保存图片
        bitmaps.clear()
        val fileDir = context.getExternalFilesDir("Pictures")
        aaa = "开始生成图片"
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
                Log.e("ScreenShot", aaa)
            }
        } catch (e: IOException) {
            // TODO: handle exception
            aaa = "生成图片失败"
            Log.e("ScreenShot", aaa)
        }
        Log.e("ScreenShot", aaa)
    }





    //595*842
    fun merge(): Bitmap {
        aaa = "开始merge"
        Log.e("ScreenShot", aaa)
        val bigbitmap =
            Bitmap.createBitmap(SCREEN_W, SCREEN_H * bitmaps.size, Bitmap.Config.ARGB_8888)
        aaa = "创建完成"
        Log.e("ScreenShot", aaa)
        val bigcanvas = Canvas(bigbitmap)
        //        Comparable(bitmaps.get(bitmaps.size() - 2),
//                bitmaps.get(bitmaps.size() - 1));
        val paint = Paint()
        var iHeight = 0
        aaa = "循环开始"
        Log.e("ScreenShot", aaa)
        for (i in bitmaps.indices) {
            var bmp: Bitmap = bitmaps[i]
            bigcanvas.drawBitmap(bmp, 0f, iHeight.toFloat(), paint)
            iHeight += bmp.height
            bmp.recycle()
        }
        aaa = "循环结束"
        Log.e("ScreenShot", aaa)
        return Bitmap.createBitmap(bigbitmap, 0, 0, bigbitmap.width, iHeight)
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