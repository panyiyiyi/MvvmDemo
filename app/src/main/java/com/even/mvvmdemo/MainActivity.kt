package com.even.mvvmdemo

import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import com.even.common.base.BaseBindActivity
import com.even.common.bean.RemindDialogBean
import com.even.common.views.RemindDialogFragment
import com.even.mvvmdemo.databinding.ActivityMainBinding

class MainActivity :
    BaseBindActivity<MainViewModel, ActivityMainBinding>(
        R.layout.activity_main, BR.mainViewModel
    ) {
    override fun initView() {
        mViewModel.req()
        mDataBinding.btn.setOnClickListener {
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
//            supportFragmentManager.beginTransaction().add(newInstance, "BaA")
//                .commitAllowingStateLoss()


            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }
}