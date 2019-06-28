package com.example.qimiao.kotlinframework.mvp.m.moudle


import com.example.qimiao.kotlinframework.mvp.m.bean.HomeBean
import com.example.qimiao.kotlinframework.network.ApiService
import com.example.qimiao.kotlinframework.network.RetrofitClient
import io.reactivex.Observable

/**
 * Created by lk on 2018/6/8.
 * model 层  用于与apiserver 交互
 */
class HomeModel {

    val retrofitClient = RetrofitClient.getInstance()
    val apiService = retrofitClient.create(ApiService::class.java)
    fun <T> loadData(url: String?, map: HashMap<String, Any>?,vararg value: Any?): Observable<T>? {
        var isfalg = true
        if (value.size == 2) {
            isfalg = false
        }
        when (isfalg) {
            true -> return apiService?.getHomeData<HomeBean>() as Observable<T>
            false -> return apiService?.getHomeMoreData<HomeBean>(value[1] as String, "2") as Observable<T>
        }
    }


    fun <T> FindData(url: String?, map: HashMap<String, Any>?,vararg value: Any?): Observable<T>? {
        return apiService?.getFindData() as Observable<T>
    }

    fun <T> sendRegisterCode(url: String?, map: HashMap<String, Any>?,vararg value: Any?): Observable<T>? {
        return apiService?.sendRegisterCode(map?.get("deviceId").toString(),map?.get("phone").toString()) as Observable<T>
    }
}