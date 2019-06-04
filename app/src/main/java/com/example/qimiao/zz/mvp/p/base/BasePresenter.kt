package com.example.qimiao.kotlinframework.mvp.p.base
/**
 * Created by lk on 2018/6/8.
 */
interface BasePresenter {
    fun<T> start(vararg value:Any)
    fun<T> start( value:HashMap<String,Any>)
}