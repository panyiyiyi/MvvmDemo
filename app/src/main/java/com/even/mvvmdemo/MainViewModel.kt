package com.even.mvvmdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.even.common.vm.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.wait

/**
 * Create by Even on 2020/8/27
 *
 */
class MainViewModel : BaseViewModel() {
    var arcLiveData = MutableLiveData<ArticleListBean>()

    fun req() {


        viewModelScope.launch {
            val job1 = requestService(
                { mRetrofitUtil.create(AppApiService::class.java).getArticleList(10) },
                arcLiveData
            )
            val job2 = requestService(
                { mRetrofitUtil.create(AppApiService::class.java).getArticleList(10) },
                arcLiveData
            )
        }

    }
}