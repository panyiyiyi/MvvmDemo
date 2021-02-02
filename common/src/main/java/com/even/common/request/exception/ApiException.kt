package com.even.common.request.exception

import com.even.common.R
import com.even.common.utils.ApplicationUtils
import org.json.JSONException
import java.io.NotSerializableException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLHandshakeException

/**
 * Created Even by 2020/10/26
 *  统一异常处理
 */
class ApiException : Exception {
    var mCode: Int
    override var message: String?

     constructor(throwable: Throwable, errorCode: Int) {
        this.mCode = errorCode
        this.message = throwable.message
    }

    override fun getLocalizedMessage(): String? {
        return message
    }

    companion object {
        fun handleException(exception: Exception): ApiException {
            val api: ApiException
            when (exception) {
                is SocketTimeoutException -> {
                    //请求超时
                    api = ApiException(exception, ApiCode.TIMEOUT_ERROR)
                    api.message =
                        ApplicationUtils.getInstance().applicationContext.getString(
                            R.string.common_connect_error
                        )
                }
                is ConnectException -> {
                    //连接异常
                    api = ApiException(exception, ApiCode.TIMEOUT_ERROR)
                    api.message =
                        ApplicationUtils.getInstance().applicationContext.getString(
                            R.string.common_connect_error
                        )
                }
                is NullPointerException -> {
                    //空指针
                    api = ApiException(exception, ApiCode.NULL_POINTER_ERROR)
                    api.message =
                        ApplicationUtils.getInstance().applicationContext.getString(
                            R.string.common_null_pointer_error
                        )
                }

                is SSLHandshakeException -> {
                    //证书校验失败
                    api = ApiException(exception, ApiCode.SSL_ERROR)
                    api.message =
                        ApplicationUtils.getInstance().applicationContext.getString(
                            R.string.common_ssl_error
                        )
                }
                is JSONException, is NotSerializableException, is ParseException, is IllegalStateException -> {
                    //解析错误
                    api = ApiException(exception, ApiCode.PARSE_ERROR)
                    api.message =
                        ApplicationUtils.getInstance().applicationContext.getString(R.string.common_parse_error)
                }
                is UnknownHostException -> {
                    //服务器异常
                    api = ApiException(exception, ApiCode.TIMEOUT_ERROR)
                    api.message =
                        ApplicationUtils.getInstance().applicationContext.getString(R.string.common_unknown_host_error)
                }
                else -> {
                    //未知异常
                    api = ApiException(exception, ApiCode.UNKNOWN)
                    api.message =
                        ApplicationUtils.getInstance().applicationContext.getString(R.string.common_unknown_error)
                }
            }
            return api
        }
    }
}