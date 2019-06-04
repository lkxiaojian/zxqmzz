package com.example.qimiao.zz.App

import android.content.Context
import android.graphics.Color
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import android.util.Log
import com.example.qimiao.zz.R
import com.example.qimiao.zz.uitls.OKHttpUpdateHttpService
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.SDKOptions
import com.netease.nimlib.sdk.StatusBarNotificationConfig
import com.netease.nimlib.sdk.auth.LoginInfo
import com.netease.nimlib.sdk.uinfo.UserInfoProvider
import com.umeng.commonsdk.UMConfigure
import com.xuexiang.xupdate.XUpdate
import com.xuexiang.xupdate.entity.UpdateError
import com.xuexiang.xupdate.utils.UpdateUtils
import com.umeng.message.PushAgent
import com.umeng.message.IUmengRegisterCallback


/**
 * Created by lk on 2018/6/12.
 */
class MyApplication : MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()
        mAppContext = applicationContext
        um()
        apkUpdate()
        wyInit()
    }

    /**
     * 网易云初始化
     */
    private fun wyInit() {
    var wi= com.example.qimiao.zz.uitls.wyInit()
        NIMClient.init(this, wi.loginInfo(), wi.options(this))
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    /**
     * 友盟初始化
     */
    private fun um() {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
        UMConfigure.init(this, "5cf2138c0cafb23a90000aea", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "1c6b5af7397c09c99da73e554cb1dc3c")
        UMConfigure.setLogEnabled(true)
        UMConfigure.setEncryptEnabled(true)

        //推送
        val mPushAgent = PushAgent.getInstance(this)
        mPushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.e("tag", "注册成功：deviceToken：-------->  $deviceToken")
            }

            override fun onFailure(s: String, s1: String) {
                Log.e("tag", "注册失败：-------->  s:$s,s1:$s1")
            }
        })

    }

    /**
     * 获取系统上下文
     */
    companion object {
        var mAppContext: Context? = null
        fun getAppContext(): Context {
            return mAppContext!!
        }
    }

    /**
     * app更新祖册
     */
    fun apkUpdate() {
        XUpdate.get()
                .debug(true)
                .isWifiOnly(false)                                               //默认设置只在wifi下检查版本更新
                .isGet(true)                                                    //默认设置使用get请求检查版本
                .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
                .param("versionCode", UpdateUtils.getVersionCode(this))         //设置默认公共请求参数
                .param("appKey", packageName)
                .setOnUpdateFailureListener { error ->
                    //设置版本更新出错的监听
                    if (error.code != UpdateError.ERROR.CHECK_NO_NEW_VERSION) {          //对不同错误进行处理
//                        ToastUtils.toast(error.toString())
                    }
                }
                .supportSilentInstall(false)                                     //设置是否支持静默安装，默认是true
                .setIUpdateHttpService(OKHttpUpdateHttpService())           //这个必须设置！实现网络请求功能。
                .init(this)
    }





}