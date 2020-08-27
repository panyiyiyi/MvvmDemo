package com.even.common.request

import okhttp3.MultipartBody
import retrofit2.http.Part
import retrofit2.http.Url

/**
 * Create by Even on 2020/8/27
 * 上传文件到服务器Api
 */
interface UploadFileApi {
    /**
     * 多个文件上传
     */
    suspend fun uploadFiles(
        @Url uploadUrl: String,//上传地址
        @Part files: List<MultipartBody.Part>//文件集合
    ): String

    /**
     * 单文件上传
     */
    suspend fun uploadFile(
        @Url uploadUrl: String,
        @Part file: MultipartBody.Part
    ): String
}