package cn.yx.pfun
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding, M : BaseWork<*>> :
    Fragment() {
    lateinit var binding: T
    lateinit var model: M
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onPrepare()
        binding = getBinding(inflater, container, savedInstanceState)
        model = getModelInstance()
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding==null
    }
    protected abstract fun onPrepare()
    protected abstract fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): T

    protected abstract fun getModelInstance(): M
    override fun onResume() {
        super.onResume()
        if (model != null) model?.onResume()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (model != null) model?.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (model != null) model?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (model != null) model?.onDestroy()
    }
}