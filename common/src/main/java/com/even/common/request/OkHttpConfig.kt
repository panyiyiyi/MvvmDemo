package com.even.common.request

import android.text.TextUtils
import com.even.common.interceptor.LogInterceptor
import com.even.common.utils.SSLUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.InputStream
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * Create by Even on 2020/8/26
 *
 */
class OkHttpConfig {
    private val mBuilder = OkHttpClient.Builder()

    private var headerMaps: Map<String, String>? = null//请求头
    private var isDebug = false  //是否调试模式
    private var readTimeout = 10L  //读超时,默认10秒
    private var writeTimeout = 10L //写超时，默认10秒
    private var connectTimeout = 10L //连接超时，默认10秒
    private var isFile: InputStream? = null  //客户端校验证书
    private var password = ""  //证书密码
    private var proxy: Proxy? = null//是否能设置代理
    private var certificates: Array<out InputStream>? = null //https证书
    private var interceptors: Array<out Interceptor>? = null //拦截器

    fun setHeaderMaps(headerMaps: Map<String, String>): OkHttpConfig {
        this.headerMaps = headerMaps
        return this
    }

    fun setDebug(debug: Boolean): OkHttpConfig {
        this.isDebug = debug
        return this
    }

    fun setReadTimeout(readTimeout: Long): OkHttpConfig {
        this.readTimeout = readTimeout
        return this
    }

    fun setWriteTimeout(writeTimeout: Long): OkHttpConfig {
        this.writeTimeout = writeTimeout
        return this
    }

    fun setConnectTimeout(connectTimeout: Long): OkHttpConfig {
        this.connectTimeout = connectTimeout
        return this
    }

    fun setIsFile(isFile: InputStream): OkHttpConfig {
        this.isFile = isFile
        return this
    }

    fun setPassword(password: String): OkHttpConfig {
        this.password = password
        return this
    }

    fun setCertificates(vararg certificates: InputStream): OkHttpConfig {
        this.certificates = certificates
        return this
    }

    fun setInterceptors(vararg interceptors: Interceptor): OkHttpConfig {
        this.interceptors = interceptors
        return this
    }

    /**
     * 设置是否能开代理
     */
    fun setProxy(proxy: Proxy): OkHttpConfig {
        this.proxy = proxy
        return this
    }


    fun build(): OkHttpClient {
        setTimeout()
        setSSLConfig()
        addInterceptors()
        setDebugConfig()
        setProxyConfig()
        return mBuilder.build()
    }

    /**
     * 设置能否代理
     */
    private fun setProxyConfig() {
        proxy?.let {
            mBuilder.proxy(proxy)
        }
    }

    /**
     * 设置调试模式不输入返回数据格式
     */
    private fun setDebugConfig() {
        if (isDebug) {
            val logInterceptor = HttpLoggingInterceptor(LogInterceptor())
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            mBuilder.addInterceptor(logInterceptor)
        }
    }


    /**
     * 配置超时时间
     */
    private fun setTimeout() {
        mBuilder.readTimeout(readTimeout, TimeUnit.SECONDS)
        mBuilder.writeTimeout(writeTimeout, TimeUnit.SECONDS)
        mBuilder.readTimeout(connectTimeout, TimeUnit.SECONDS)
        mBuilder.retryOnConnectionFailure(true)
    }


    /**
     * 添加配置传递过来得拦截器
     */
    private fun addInterceptors() {
        interceptors?.forEach {
            mBuilder.addInterceptor(it)
        }
    }

    /**
     * 配置证书
     */
    private fun setSSLConfig() {
        var sslParam: SSLUtils.SSLParams = if (null == certificates) {
            //信任所有证书
            SSLUtils.getSSLSocketFactory()
        } else {
            if (null != isFile && !TextUtils.isEmpty(password)) {
                //使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务器证书（自签名证书）
                SSLUtils.getSSLSocketFactory(isFile!!, password, *certificates!!)
            } else {
                //使用预埋证书，校验服务器证书（自签名证书）
                SSLUtils.getSSLSocketFactory(*certificates!!)
            }
        }
        mBuilder.sslSocketFactory(sslParam.mSSLSocketFactory, sslParam.trustManager)
    }
}