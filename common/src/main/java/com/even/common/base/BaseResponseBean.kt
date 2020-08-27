package com.even.common.base

open class BaseResponseBean<T> {
    val errorCode: Int = 0
    val errorMsg: String? = null
    val data: T? = null
}