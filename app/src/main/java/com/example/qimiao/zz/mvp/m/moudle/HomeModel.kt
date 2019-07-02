package com.example.qimiao.kotlinframework.mvp.m.moudle


import com.example.qimiao.kotlinframework.mvp.m.bean.HomeBean
import com.example.qimiao.kotlinframework.network.ApiService
import com.example.qimiao.kotlinframework.network.RetrofitClient
import com.example.qimiao.zz.uitls.Constant
import io.reactivex.Observable
import java.lang.reflect.Array
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by lk on 2018/6/8.
 * model 层  用于与apiserver 交互
 */
class HomeModel {

    val retrofitClient = RetrofitClient.getInstance()
    val apiService = retrofitClient.create(ApiService::class.java)
    fun <T> loadData(url: String?, map: HashMap<String, Any>?, vararg value: Any?): Observable<T>? {
        var isfalg = true
        if (value.size == 2) {
            isfalg = false
        }
        when (isfalg) {
            true -> return apiService?.getHomeData<HomeBean>() as Observable<T>
            false -> return apiService?.getHomeMoreData<HomeBean>(value[1] as String, "2") as Observable<T>
        }
    }


    fun <T> FindData(url: String?, map: HashMap<String, Any>?, vararg value: Any?): Observable<T>? {
        return apiService?.getFindData() as Observable<T>
    }

    /**
     * 获取验证吗
     */
    fun <T> sendRegisterCode(url: String?, map: HashMap<String, Any>?, vararg value: Any?): Observable<T>? {
        return apiService?.sendRegisterCode(map?.get("deviceId").toString(), map?.get("phone").toString()) as Observable<T>
    }

    /**
     * 用户注册
     */
    fun <T> registerCode(url: String?, map: HashMap<String, Any>?, vararg value: Any?): Observable<T>? {
        return apiService?.registerCode(map?.get("deviceId").toString(), map?.get("phone").toString(), map?.get("smsCode").toString(), map?.get("password").toString()) as Observable<T>
    }

    /**
     * 找回密码
     */
    fun <T> retrievePassword(url: String?, map: HashMap<String, Any>?, vararg value: Any?): Observable<T>? {
        return apiService?.retrievePassword(map?.get("deviceId").toString(), map?.get("phone").toString(), map?.get("smsCode").toString(), map?.get("password").toString()) as Observable<T>
    }

    /**
     * 登入
     * @Query("username") username: String,
    @Query("password") password: String,
    @Query("grant_type") grant_type: String,
    @Query("client_secret") client_secret: String,
    @Query("client_id") client_id: String,
    @Query("authen_type") authen_type: String
     */

    fun <T> oauthToken(url: String?, map: HashMap<String, Any>?, vararg value: Any?): Observable<T>? {
        return apiService?.oauthToken(map?.get("username").toString(), map?.get("password").toString(), "password", "123456", "jxdou_web", map?.get("authen_type").toString()) as Observable<T>
    }


    /**
     * 检查token
     */
    fun <T> checkToken(url: String?, map: HashMap<String, Any>?, vararg value: Any?): Observable<T>? {
        var data = value[0] as kotlin.Array<Any>
        return apiService?.checkToken(data[0].toString()) as Observable<T>
    }

    /**
     * 刷新token
     * @Query("refresh_token") refresh_token: String,
    @Query("grant_type") grant_type: String,
    @Query("client_id") client_id: String,
    @Query("client_secret") client_secret: String
     */
    fun <T> refreshToken(url: String?, map: HashMap<String, Any>?, vararg value: Any?): Observable<T>? {
        return apiService?.refreshToken(map?.get("refresh_token").toString(), "refresh_token", "jxdou_web", "123456",Constant.access_token) as Observable<T>
    }


}