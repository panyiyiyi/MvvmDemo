package com.even.common.bean

/**
 * 请求错误对象处理
 */
data class ErrorBean(val code: Int, val error: String?, val isShow: Boolean = true)