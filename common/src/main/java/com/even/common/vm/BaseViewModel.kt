package com.even.common.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.even.common.base.BaseResponseBean
import com.even.common.bean.ErrorBean
import com.even.common.request.RetrofitUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel() {
     var mIsShowLoading = MutableLiveData<Boolean>()//是否显示加载框
     var mErrorData = MutableLiveData<ErrorBean>()//请求错误返回对象

    val mRetrofitUtil by lazy { RetrofitUtil.getInstance() }


    /**
     * 请求服务器接口
     */
    fun <T> requestService(
        block: suspend CoroutineScope.() -> BaseResponseBean<T>,
        liveData: MutableLiveData<T>,
        isShowLoading: Boolean = false,
        isShowError: Boolean = true
    ) {
        if (isShowLoading) mIsShowLoading.value = true
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { block() }
                if (result.errorCode == 0) {
                    liveData.value = result.data
                } else {
                    //返回错误
                    mErrorData.value = ErrorBean(result.errorCode, result.errorMsg, isShowError)
                }
            } catch (e: Exception) {

            } finally {
                mIsShowLoading.value = false
            }
        }
    }

}