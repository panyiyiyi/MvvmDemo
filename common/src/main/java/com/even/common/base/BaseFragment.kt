package com.even.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.even.common.vm.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * Create by Even on 2020/8/27
 * fragment基础封装
 */
abstract class BaseFragment<VM : BaseViewModel> : Fragment() {
    lateinit var mViewModel: VM
    lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val clazz =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this).get(clazz)
        if (getVariable() != null) {
            val dataBinding =
                DataBindingUtil.inflate<ViewDataBinding>(inflater, getLayoutId(), container, false)
            dataBinding.lifecycleOwner = this
            dataBinding.setVariable(getVariable()!!, mViewModel)
            mView = dataBinding.root
        } else {
            mView = inflater.inflate(getLayoutId(), container, false)
        }
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        initData()
    }

    //布局ID
    abstract fun getLayoutId(): Int

    //绑定参数
    abstract fun getVariable(): Int?

    //初始化View
    abstract fun initView()

    //初始化数据
    open fun initData() {}

}