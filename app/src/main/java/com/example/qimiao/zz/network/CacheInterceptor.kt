package com.example.qimiao.kotlinframework.network

import android.content.Context
import com.example.urilslibrary.Utils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
/**
 * Created by lk on 2018/6/8.
 */
class CacheInterceptor(context: Context) : Interceptor {
    val context = context
    override fun intercept(chain: Interceptor.Chain?): Response? {
        var request = chain?.request()
        if (Utils.isNetworkAvailable(context)) {
            val response = chain?.proceed(request)
            // read from cache for 60 s
            val maxAge = 60
            return response?.newBuilder()?.removeHeader("Pragma")?.removeHeader("Cache-Control")?.header("Cache-Control", "public, max-age=" + maxAge)?.build()
        } else {
            request = request?.newBuilder()?.cacheControl(CacheControl.FORCE_CACHE)?.build()
            val response = chain?.proceed(request)
            //set cahe times is 3 days
            val maxStale = 60 * 60 * 24 * 3
            return response?.newBuilder()?.removeHeader("Pragma")?.removeHeader("Cache-Control")?.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)?.build()
        }
    }
}
