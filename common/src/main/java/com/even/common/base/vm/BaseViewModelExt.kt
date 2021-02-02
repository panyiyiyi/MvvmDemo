package com.even.common.base.vm

import androidx.lifecycle.MutableLiveData
import com.even.common.base.beans.BaseResponse
import com.even.common.base.beans.BaseResult
import com.even.common.request.exception.ApiCode
import com.even.common.request.exception.ApiException
import com.even.common.vm.BaseViewModel

/**
 * Created Even by 2021/2/1
 */



suspend inline fun <reified T> BaseViewModel.apiResult(
    liveData: MutableLiveData<T>? = null,
    crossinline blockSuccess: (data: BaseResult<T>) -> Unit = {},
    crossinline blockError: (code: Int?, msg: String?) -> Unit? = { _, _ -> },
    isShowLoading: Boolean = false,
    isShowError: Boolean = true,
    crossinline block: suspend () -> BaseResult<T>
): BaseResponse<BaseResult<T>> {
    return try {
        mIsShowLoading.postValue(true)
        val response = block.invoke()
        if (response.code == ApiCode.SUCCESS) {
            liveData?.postValue(response.data)
            BaseResponse.Success(response)
        } else {
            BaseResponse.Error(response.code, response.message)
        }
    } catch (e: Exception) {
        val handleException = ApiException.handleException(e)
        BaseResponse.Error(handleException.mCode, handleException.message)
    } finally {
        mIsShowLoading.postValue(false)
    }
}