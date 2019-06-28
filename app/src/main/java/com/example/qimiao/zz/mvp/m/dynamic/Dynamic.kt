package com.example.qimiao.zz.mvp.m.dynamic
import android.support.annotation.Keep
import android.util.Log
import java.lang.reflect.Method


/**
 * Created by lk on 2018/7/18.
 * 通过反射 来动态处理网络请求
 */
@Keep
class Dynamic {
    companion object {
        fun <T> invoke(className: String, methodName: String, url: String?, map: HashMap<String, Any>?, vararg value: Any): T? {

            val clazz = Class.forName(className)
            val instance = clazz.newInstance()
            val methods = clazz.declaredMethods
            var executeMethods: Method? = null

            for (method in methods) {
                if (method.name == methodName) {
                    executeMethods = method
                    break
                }
            }
            executeMethods?.isAccessible = true
            Log.e("executeMethods", "executeMethods--" + executeMethods)
            return executeMethods?.invoke(instance,url,map, value) as T?
        }
    }
}