package com.even.common.base

import android.app.Application
import com.even.common.request.OkHttpConfig
import com.even.common.request.RetrofitUtil
import java.net.Proxy

/**
 * Create by Even on 2020/8/26
 *
 */
open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    /**
     * 初始化网络请求
     * @param hostUrl 域名
     * @param isDebug 是否是测试模式
     */
    open fun initHttp(hostUrl: String, isDebug: Boolean) {
        val okHttpConfig = OkHttpConfig()
        okHttpConfig.setDebug(isDebug)
        if (!isDebug) {
            okHttpConfig.setProxy(Proxy.NO_PROXY)
        }
        RetrofitUtil.getInstance().setBaseUrl(hostUrl).setClient(okHttpConfig.build())
            .buildRetrofit()
    }
}