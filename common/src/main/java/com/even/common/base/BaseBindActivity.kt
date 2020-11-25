package com.even.common.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.even.common.vm.BaseViewModel

/**
 * Created Even by 2020/10/26
 *
 */
abstract class BaseBindActivity<VM : BaseViewModel, T : ViewDataBinding>(
    @LayoutRes private val layoutId: Int,
    private val variableId: Int
) :
    BaseActivity<VM>(layoutId) {

    lateinit var mDataBinding: T
    override fun initView(view: ViewGroup) {
        mDataBinding = if (useDefaultTitleBar()) {
            //使用默认标题
            DataBindingUtil.bind(view[1])!!
        } else {
            DataBindingUtil.bind(view[0])!!
        }

        mDataBinding.lifecycleOwner = this
        mDataBinding.setVariable(variableId, mViewModel)

        initView()
    }

    abstract fun initView()
}