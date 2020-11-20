package com.even.mvvmdemo

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.even.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_second.*

/**
 * Create by Even on 2020/8/27
 *
 */
class SecondActivity : BaseActivity<SecondViewModel>(R.layout.activity_second) {
    override fun initView(view: ViewGroup) {

        mViewModel.req()
        mViewModel.arcLiveData.observe(this, Observer {
            it.datas?.let {
                tvText.text = it[1].author
                Toast.makeText(this, it[1].author, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun useDefaultTitleBar(): Boolean = false

}