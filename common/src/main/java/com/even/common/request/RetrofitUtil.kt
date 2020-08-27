package com.even.common.request

import com.even.common.interceptor.HeaderInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Create by Even on 2020/8/26
 * retrofit配置
 */
class RetrofitUtil {
    private val mBuilder =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
    private lateinit var mRetrofit: Retrofit
    private lateinit var mOkHttpClient: OkHttpClient
    private var mHeaderInterceptor: HeaderInterceptor? = null

    fun <T> create(clazz: Class<T>): T {
        return mRetrofit.create(clazz)
    }

    fun buildRetrofit() {
        mRetrofit = mBuilder.build()
    }

    //添加请求头
    fun addHeader(key: String, value: String) {
        if (null == mHeaderInterceptor) {
            mHeaderInterceptor = HeaderInterceptor()
        }
        mHeaderInterceptor!!.mHeaderMaps[key] = value
        mOkHttpClient = mOkHttpClient.newBuilder().addInterceptor(mHeaderInterceptor!!).build()
    }

    fun setBaseUrl(baseUrl: String): RetrofitUtil {
        mBuilder.baseUrl(baseUrl)
        return this
    }

    fun setClient(client: OkHttpClient): RetrofitUtil {
        mOkHttpClient = client
        mBuilder.client(client)
        return this
    }

    companion object {
        private var instance: RetrofitUtil? = null
        fun getInstance(): RetrofitUtil {
            if (null == instance) {
                synchronized(RetrofitUtil::class) {
                    if (null == instance) {
                        instance = RetrofitUtil()
                    }
                }
            }
            return instance!!
        }
    }
}