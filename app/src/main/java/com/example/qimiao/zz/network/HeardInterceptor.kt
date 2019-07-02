package com.example.qimiao.kotlinframework.network

import android.content.Context
import com.example.qimiao.zz.uitls.Constant
import com.example.urilslibrary.Utils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by lk on 2018/6/8.
 */
class HeardInterceptor(context: Context) : Interceptor {
    val context = context
    override fun intercept(chain: Interceptor.Chain?): Response? {
        var request = chain?.request()
        request?.newBuilder()
                ?.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                ?.addHeader("Accept-Encoding", "gzip, deflate")
                ?.addHeader("Connection", "keep-alive")
                ?.addHeader("Accept", "*/*")
                ?.addHeader("Cookie", "add cookies here")
                ?.build()
        return chain?.proceed(request)
    }
//    ?.addHeader("Bearer", Constant.access_token)
}
