package com.example.qimiao.zz.mvp.p

import com.example.qimiao.kotlinframework.mvp.m.moudle.HomeModel
import com.example.qimiao.zz.App.MyApplication
import com.example.qimiao.zz.mvp.m.dynamic.Dynamic
import com.example.qimiao.zz.mvp.v.Contract
import com.example.qimiao.zz.uitls.applySchedulers
import com.example.urilslibrary.Utils
import io.reactivex.Observable

/**
 * Created by lk on 2018/6/8.
 */
class ParsingPresenter(view: Contract.View) : Contract.Presenter {



    var mView: Contract.View? = null
    val mModel: HomeModel by lazy {
        HomeModel()
    }

    init {
        mView = view
    }

    /**
     * value[0]  返回类型  多个请求同时进行 ，根据此字段来判断  必传
     * value[1]  请求网络的方法名  必传
     * value[2]  如果是用rf  直接用 URL 来请求的url地址
     * value[3]  如果 是请求 网络的时候，有表单  此map为表单
     * value[4]  用 rf 请求所带的参数     如 fun <T> getHomeMoreData(@Query("date") date: String, @Query("num") num: String): Observable<HomeBean>
     * date  num  传递的就是这两个的值
     */
    override fun <T> start(vararg value: Any) {
        if (value == null || value.size < 2) {
            return
        }
        var type: String = value[0] as String
        var method: String = value[1] as String
        var url: String? = null
        if (value.size > 2) {
            url = value[2] as String
        }

        var map: HashMap<String, Any>? = null
        if (value.size == 4) {
            map = value[3] as HashMap<String, Any>
        }
        requestData<T>(type, method, url, map)
    }

    /**
     * 传递参数 通过map的方式
     */
    override fun <T> start(value: HashMap<String, Any>) {

        requestData<T>(
                value.get("type") as String,//返回类型  多个请求同时进行 ，根据此字段来判断
                value.get("method") as String,  //请求网络的方法名
                value.get("url") as? String,  //如果是用rf  直接用 URL 来请求的url地址
                value.get("map") as? HashMap<String, Any>,//如果 是请求 网络的时候，有表单  此map为表单
                value.get("param") //用 rf 请求所带的参数     如 fun <T> getHomeMoreData(@Query("date") date: String, @Query("num") num: String): Observable<HomeBean>
                //date  num  传递的就是这两个的值
        )
    }

    override fun <T> requestData(type: String, method: String, url: String?, map: HashMap<String, Any>?, vararg param: Any?) {
        //通过反射进行动态代理
        val observable: Observable<T>? = let { Dynamic.invoke(mModel.javaClass.name, method, url,map,param) }
        //p层与v层的交互     对view 返回数据
        CustomData(observable, type)
    }

    /**
     * 加载更多  可以用start  代替
     * 也可以改写为  动态代理 的 方式来实现  根据自己的项目  需求来进行抉择
     */
    fun <T> moreData(data: String?, url: String, type: String, map: HashMap<String, Any>?) {

        when (type) {
            "loadData" -> {
                val observable: Observable<T>? = let { mModel.loadData(url,map,false, data!!) }
                CustomData(observable, type)
            }
            "sendRegisterCode"->{
                val observable: Observable<T>? = let { mModel.loadData(url,map,false, data!!) }
                CustomData(observable, type)
            }
        }
    }

    fun <T> CustomData(observable: Observable<T>?, type: String) {
        if (mView == null) {
            return
        }
        observable?.applySchedulers()?.subscribe({ beans: T ->
            mView?.setData(type, beans)
        }, { error: Throwable ->
            mView?.onError(type, error)
        })

    }
}


