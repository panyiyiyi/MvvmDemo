package com.even.mvvmdemo

import androidx.lifecycle.MutableLiveData
import com.even.common.vm.BaseViewModel

/**
 * Create by Even on 2020/8/27
 *
 */
class MainViewModel : BaseViewModel() {
    var arcLiveData = MutableLiveData<ArticleListBean>()

    fun req() {
        requestService(
            { mRetrofitUtil.create(AppApiService::class.java).getArticleList(10) },
            arcLiveData
        )
    }
}