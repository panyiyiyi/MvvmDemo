package com.even.mvvmdemo

import android.graphics.Color
import android.view.Gravity
import com.even.common.base.BaseActivity
import com.even.common.bean.RemindDialogBean
import com.even.common.views.RemindDialogFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getVariable(): Int = BR.mainViewModel

    override fun initView() {
        mViewModel.req()

        btn.setOnClickListener {
            val newInstance = RemindDialogFragment.newInstance(
                RemindDialogBean(
                    "sdfasdfa",
                    "hhddhh",
                    "ada",
                    "adfasd",
                    Gravity.START,
                    Gravity.CENTER,
                    Color.BLUE,
                    Color.RED,
                    Color.BLUE,
                    false
                )
            )
            supportFragmentManager.beginTransaction().add(newInstance, "BaA")
                .commitAllowingStateLoss()


//            val intent = Intent(this, SecondActivity::class.java)
//            startActivity(intent)
        }
    }

    override fun getTitleBarId(): Int = R.layout.item_title_bar
}