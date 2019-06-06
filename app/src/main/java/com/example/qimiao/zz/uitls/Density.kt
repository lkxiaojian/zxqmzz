package com.example.qimiao.zz.uitls

import android.content.ComponentCallbacks
import android.app.Activity
import android.app.Application
import android.content.res.Configuration


class Density {


    companion object {
        val WIDTH = 320f//参考设备的宽，单位是dp 320 / 2 = 160
        var appDensity: Float = 0.toFloat()//表示屏幕密度
        var appScaleDensity: Float = 0.toFloat() //字体缩放比例，默认appDensity
        @JvmStatic
        fun setDensity(application: Application, activity: Activity) {
            //获取当前app的屏幕显示信息
            val displayMetrics = application.resources.displayMetrics
            if (appDensity == 0f) {
                //初始化赋值操作
                appDensity = displayMetrics.density
                appScaleDensity = displayMetrics.scaledDensity

                //添加字体变化监听回调
                application?.registerComponentCallbacks(object : ComponentCallbacks {
                    override fun onLowMemory() {

                    }
                    override fun onConfigurationChanged(newConfig: Configuration?) {
                        //字体发生更改，重新对scaleDensity进行赋值
                        if (newConfig != null && newConfig.fontScale > 0) {
                            appScaleDensity = application.resources.displayMetrics.scaledDensity;
                        }
                    }
                }
                )
            }

            //计算目标值density, scaleDensity, densityDpi
            val targetDensity = displayMetrics.widthPixels / WIDTH // 1080 / 360 = 3.0
            val targetScaleDensity = targetDensity * (appScaleDensity / appDensity)
            val targetDensityDpi = (targetDensity * 160).toInt()

            //替换Activity的density, scaleDensity, densityDpi
            val dm = activity.resources.displayMetrics
            dm.density = targetDensity
            dm.scaledDensity = targetScaleDensity
            dm.densityDpi = targetDensityDpi
        }
    }

}