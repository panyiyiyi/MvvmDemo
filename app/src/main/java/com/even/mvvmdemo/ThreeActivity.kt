package com.even.mvvmdemo

import com.even.common.base.BaseActivity
import com.even.common.vm.BaseViewModel
import kotlinx.android.synthetic.main.activity_second.*

/**
 * Create by Even on 2020/8/27
 *
 */
class ThreeActivity : BaseActivity<BaseViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_second

    override fun getVariable(): Int? = null

    override fun initView() {
        tvText.text = "hhhhhhhhh"
    }
}