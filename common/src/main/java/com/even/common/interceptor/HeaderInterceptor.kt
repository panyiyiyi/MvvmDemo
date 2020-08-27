package com.even.common.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Create by Even on 2020/8/26
 *
 */
class HeaderInterceptor : Interceptor {
    val mHeaderMaps = mutableMapOf<String, String>()
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (mHeaderMaps.isNotEmpty()) {
            mHeaderMaps.forEach {
                builder.addHeader(it.key, it.value)
            }
        }
        return chain.proceed(builder.build())
    }
}