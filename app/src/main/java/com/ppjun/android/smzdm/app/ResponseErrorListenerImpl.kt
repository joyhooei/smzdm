package com.ppjun.android.smzdm.app

import android.content.Context
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.jess.arms.utils.ArmsUtils
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener
import org.json.JSONException
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

class ResponseErrorListenerImpl : ResponseErrorListener {
    override fun handleResponseError(context: Context?, t: Throwable?) {
        Timber.tag("Catch-Error").w(t?.message)
        var msg = "未知错误"
        when (t) {
            is UnknownHostException -> {
                msg = "网络不可用"
            }
            is SocketTimeoutException -> {
                msg = "请求网络超时"
            }
            is HttpException -> {
                val httpException = t
                msg =convertStatusCode(httpException)
            }
            is JsonParseException, is ParseException, is JSONException, is JsonIOException -> {
                msg = "数据解析错误"
            }
        }
        ArmsUtils.snackbarText(msg)
    }


    fun convertStatusCode(httpException: HttpException): String {
        var msg = ""


        when (httpException.code()) {
            500 -> {
                msg = "服务器发生错误"
            }
            404 -> {
                msg = "请求地址不存在"
            }
            403 -> {
                msg = "请求被服务器拒绝"
            }
            307 -> {
                msg = "请求被重定向到其他页面"
            }
            else -> {
                msg = httpException.message()
            }
        }
        return msg


    }
}