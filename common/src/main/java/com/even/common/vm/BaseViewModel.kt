package com.even.common.vm

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.even.common.base.BaseResponseBean
import com.even.common.bean.ErrorBean
import com.even.common.bean.TitleBarBean
import com.even.common.request.RetrofitUtil
import com.even.common.request.exception.ApiException
import com.even.common.utils.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel() {
    val mIsShowLoading = MutableLiveData<Boolean>()//是否显示加载框
    val mErrorData = MutableLiveData<ErrorBean>()//请求错误返回对象

    val titleBar = MutableLiveData<TitleBarBean>()//标题栏
    val titleBackListener = ObservableField<View.OnClickListener>()//返回键点击监听，用来重写返回方法
    val titleBarListener = ObservableField<View.OnClickListener>() //标题栏点击监听

    val mRetrofitUtil by lazy { RetrofitUtil.getInstance() }

    /**
     * 请求服务器接口
     */
    fun <T> requestService(
        block: suspend CoroutineScope.() -> BaseResponseBean<T>,
        liveData: MutableLiveData<T>,
        blockSuccess: (data: T?) -> Unit? = { },
        blockError: (code: Int, msg: String) -> Unit? = { _, _ -> },
        isShowLoading: Boolean = false,
        isShowError: Boolean = true
    ) {
        LogUtils.e("执行时间：${System.currentTimeMillis()}")
        if (isShowLoading) mIsShowLoading.postValue(true)
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { block() }
                if (result.errorCode == 0) {
                    liveData.postValue(result.data)
                } else {
                    //返回错误
                    mErrorData.postValue(ErrorBean(result.errorCode, result.errorMsg, isShowError))
                }
            } catch (e: Exception) {
                val exception = ApiException.handleException(e)
                mErrorData.postValue(ErrorBean(exception.mCode, exception.message, isShowError))
            } finally {
                mIsShowLoading.postValue(false)
            }
        }
    }

}