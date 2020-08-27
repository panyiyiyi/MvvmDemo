package com.even.common.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.even.common.vm.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * Create by Even on 2020/8/25
 * Activity 基础类
 */
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {
    open lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val clazz =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this).get(clazz)
        if (getVariable() != null) {
            val dataBinding =
                DataBindingUtil.setContentView<ViewDataBinding>(this, getLayoutId())
            dataBinding.lifecycleOwner = this
            dataBinding.setVariable(getVariable()!!, mViewModel)
            setContentView(dataBinding.root)
        } else {
            setContentView(getLayoutId())
        }

        initView()
        initData()
    }


    //布局Id
    @LayoutRes
    abstract fun getLayoutId(): Int

    //绑定参数
    abstract fun getVariable(): Int?

    //初始化布局相关
    abstract fun initView()

    //获取数据
    open fun initData() {}

}