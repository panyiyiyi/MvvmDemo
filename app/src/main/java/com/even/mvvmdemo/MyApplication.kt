package com.even.mvvmdemo

import com.even.common.base.BaseApplication

/**
 * Create by Even on 2020/8/27
 *
 */
class MyApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        initHttp("https://www.wanandroid.com/", BuildConfig.DEBUG)
    }
}