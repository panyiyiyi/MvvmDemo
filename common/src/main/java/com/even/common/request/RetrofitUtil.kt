package com.even.common.request

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * Create by Even on 2020/8/26
 * retrofit配置
 */
class RetrofitUtil {
    private val mBuilder by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
    }
    private lateinit var mRetrofit: Retrofit
    private lateinit var mOkHttpClient: OkHttpClient

    fun <T> create(clazz: Class<T>): T {
        return mRetrofit.create(clazz)
    }

    //多图片上传不带参数
    suspend fun uploadFiles(
        uploadUrl: String,  //服务器路径
        fileMaps: Map<String, String>,//文件数据
        retrofit: Retrofit
    ): String {
        return uploadFilesWithParams(uploadUrl, null, fileMaps, retrofit)
    }

    //多图上传并且带参数
    suspend fun uploadFilesWithParams(
        uploadUrl: String,  //服务器路径
        paramMaps: Map<String, Any>?,//传递的参数
        fileMaps: Map<String, String>,//传递的文件信息：文件名以及文件路径
        retrofit: Retrofit
    ): String {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        paramMaps?.forEach { builder.addFormDataPart(it.key, it.value.toString()) }
        fileMaps.forEach {
            val file = File(it.value)
            val fileBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            builder.addFormDataPart(it.key, file.name, fileBody)
        }
        val parts = builder.build().parts
        return retrofit.create(UploadFileApi::class.java).uploadFiles(uploadUrl, parts)
    }

    fun buildRetrofit() {
        mRetrofit = mBuilder.build()
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