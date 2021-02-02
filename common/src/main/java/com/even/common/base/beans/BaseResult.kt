package com.even.common.base.beans

/**
 * Created Even by 2021/2/1
 * 基础返回对象
 */
data class BaseResult<T>(
    val code: Int,
    val message: String?,
    val data: T
)