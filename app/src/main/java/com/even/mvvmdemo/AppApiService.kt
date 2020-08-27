package com.even.mvvmdemo

import com.even.common.base.BaseResponseBean
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Create by Even on 2020/8/27
 *
 */
interface AppApiService {

    @GET("article/listproject/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): BaseResponseBean<ArticleListBean>
}