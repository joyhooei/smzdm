package com.ppjun.android.smzdm.app

import android.content.Context
import com.jess.arms.http.GlobalHttpHandler
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class GlobalHttpHandlerImpl(var context: Context) : GlobalHttpHandler {


    override fun onHttpRequestBefore(chain: Interceptor.Chain?, request: Request?): Request {

        // return  chain!!.request().newBuilder().header("token","").build()
        return requireNotNull(request)
    }

    override fun onHttpResultResponse(httpResult: String?, chain: Interceptor.Chain?, response: Response?): Response {
/* 这里可以先客户端一步拿到每一次http请求的结果,可以解析成json,做一些操作,如检测到token过期后
                       重新请求token,并重新执行请求 */
       // if (httpResult!!.isNotEmpty() && RequestInterceptor.isJson(response!!.body()!!.contentType())) {


          //  var list = ArmsUtils.obtainAppComponentFromContext(context).gson().fromJson<Response>(httpResult, object : TypeToken<Response>() {}.type)


       // }

        return response!!
    }
}