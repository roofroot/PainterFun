package cn.open.book.application

import android.app.Application
import android.content.res.Resources
import cn.yx.pfun.SCREEN_DENSITY
import cn.yx.pfun.SCREEN_H
import cn.yx.pfun.SCREEN_W
import cn.yx.pfun.tools.ActivityManager

/**
 * yuxiu
 * 2022/10/4
 **/
class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        SCREEN_W = Resources.getSystem().getDisplayMetrics().widthPixels;
        SCREEN_H = Resources.getSystem().getDisplayMetrics().heightPixels;
        SCREEN_DENSITY = Resources.getSystem().displayMetrics.density
    }

}