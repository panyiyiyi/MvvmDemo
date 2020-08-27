package com.even.mvvmdemo

import android.content.Intent
import com.even.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getVariable(): Int = BR.mainViewModel

    override fun initView() {
        mViewModel.req()

        btn.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getTitleBarId(): Int = R.layout.item_title_bar
}