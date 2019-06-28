package com.example.qimiao.zz.mvp.v

import com.example.qimiao.kotlinframework.mvp.p.base.BasePresenter
import com.example.qimiao.kotlinframework.mvp.v.base.BaseView
import java.util.*

/**
 * Created by lk on 2018/6/8.
 */
interface Contract {
    interface View : BaseView<Presenter> {
        fun <T> setData(type: String, bean: T)
        fun  onError( type: String,error: Throwable)
    }

    interface Presenter : BasePresenter {
        fun <T> requestData(type: String, method:String,url: String?, map: HashMap<String, Any>?,vararg param: Any?)
    }

}