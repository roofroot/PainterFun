package cn.yx.pfun.ui.activity

import cn.yx.pfun.MyBaseActivity
import cn.yx.pfun.databinding.ActivityMainBinding
import cn.yx.pfun.ui.work.MainWork

/**
 * yuxiu
 * 2022/10/4
 **/
class MainActivity : MyBaseActivity<ActivityMainBinding, MainWork>() {
    override fun onPrepare() {

    }

    override fun getBindingInstance(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getModelInstance(): MainWork {
        return MainWork(binding,this)
    }

}