package com.example.qimiao.kotlinframework.network

import com.example.qimiao.kotlinframework.mvp.m.bean.HomeBean
import com.example.qimiao.zz.mvp.m.bean.FindBean
import com.example.qimiao.zz.mvp.m.bean.LoginToken
import com.example.qimiao.zz.mvp.m.bean.RefreshTokenBean
import com.example.qimiao.zz.mvp.m.bean.ResultCode
import com.example.qimiao.zz.uitls.Constant
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by lk on 2018/6/8.
 */
interface ApiService<T> {
    companion object {
        val BASE_URL: String
            get() = "http://192.168.3.221:8080/"
    }

    //获取首页第一页数据
    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun <T> getHomeData(): Observable<HomeBean>


    //获取首页第一页之后的数据  ?date=1499043600000&num=2
    @GET("v2/feed")
    fun <T> getHomeMoreData(@Query("date") date: String, @Query("num") num: String): Observable<HomeBean>

    //    //获取发现频道信息T
    @GET("v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getFindData(): Observable<MutableList<FindBean>>

    /**
     * 获取验证吗
     */

//    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("send-validate-code")
    fun sendRegisterCode(@Query("deviceId") deviceId: String, @Query("phone") phone: String): @JvmSuppressWildcards Observable<ResultCode>

    /**
     * 用户注册
     */
//    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("register")
    fun registerCode(@Query("deviceId") deviceId: String, @Query("phone") phone: String, @Query("smsCode") smsCode: String, @Query("password") password: String): @JvmSuppressWildcards Observable<ResultCode>

    /**
     * 找回密码
     */
//    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("retrieve-password")
    fun retrievePassword(@Query("deviceId") deviceId: String, @Query("phone") phone: String, @Query("smsCode") smsCode: String, @Query("password") password: String): @JvmSuppressWildcards Observable<ResultCode>

    /**
     * 登入
     */
    @POST("oauth/token")
    fun oauthToken(@Query("username") username: String,
                   @Query("password") password: String,
                   @Query("grant_type") grant_type: String,
                   @Query("client_secret") client_secret: String,
                   @Query("client_id") client_id: String,
                   @Query("authen_type") authen_type: String): @JvmSuppressWildcards Observable<LoginToken>


    /**
     * 检查token
     */
    @GET("oauth/check_token")
    fun checkToken(@Query("token") token: String): Observable<RefreshTokenBean>


    /**
     * 刷新token
     */
    @POST("oauth/token")
    fun refreshToken(@Query("refresh_token") refresh_token: String,
                     @Query("grant_type") grant_type: String,
                     @Query("client_id") client_id: String,
                     @Query("client_secret") client_secret: String,
                     @Header("Bearer") Bearer: String): @JvmSuppressWildcards Observable<LoginToken>


}