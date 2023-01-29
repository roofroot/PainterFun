package cn.yx.pfun.tools

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi


class ActivityManager() {
    lateinit var context: Context

    fun  init(context: Context) {
        this.context = context
    }


    companion object {
        @Volatile
        private var manager: ActivityManager? = null
        fun getInstance() =
            manager ?: synchronized(this) {
                manager ?: ActivityManager().also { manager = it }
            }

    }

    fun <T : Activity> startActivity(context: Context,clazz: Class<T>) {
        context.startActivity(Intent(context, clazz))
    }
    fun <T : Activity> startActivity(clazz: Class<T>) {
        context.startActivity(Intent(context, clazz))
    }
    @RequiresApi(Build.VERSION_CODES.S)
    fun <T : Activity> startActivityByName(clazz: Class<T>) {
        val intent=Intent()
        intent.setClassName(clazz.packageName, clazz.simpleName)
        context.startActivity(Intent())
    }

    fun startActivityByName(packageName:String,className:String) {
        val intent=Intent()
        intent.setClassName(packageName, className)
        context.startActivity(Intent())
    }
}