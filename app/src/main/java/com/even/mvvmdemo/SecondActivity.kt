package com.even.mvvmdemo

import android.widget.Toast
import androidx.lifecycle.Observer
import com.even.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_second.*

/**
 * Create by Even on 2020/8/27
 *
 */
class SecondActivity : BaseActivity<SecondViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_second

    override fun getVariable(): Int? = null

    override fun initView() {
        mViewModel.req()
        mViewModel.arcLiveData.observe(this, Observer {
            it.datas?.let {
                tvText.text = it[1].author
                Toast.makeText(this, it[1].author, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun initData() {
        super.initData()
    }
}