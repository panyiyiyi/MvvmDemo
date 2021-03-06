package com.even.common.request.exception

/**
 * @author  Created by Even on 2019/8/7
 *  Email: emailtopan@163.com
 * 自定义异常错误Code,与服务器的错误码无关联，如果要判断服务器的错误码，需在做处理
 */
object ApiCode {
    //返回成功
    const val SUCCESS = 0

    /**
     * 未知错误
     */
    const val UNKNOWN = 1000

    /**
     * 超时
     */
    const val TIMEOUT_ERROR = 1001

    /**
     * 空指针
     */
    const val NULL_POINTER_ERROR = 1002

    /**
     * 证书错误
     */
    const val SSL_ERROR = 1003

    /**
     * 类型转换
     */
    const val CONVERSION_ERROR = 1004

    /**
     * 解析错误
     */
    const val PARSE_ERROR = 1005

    /**
     * 非法数据
     */
    const val ILLEGAL_ERROR = 1006
}