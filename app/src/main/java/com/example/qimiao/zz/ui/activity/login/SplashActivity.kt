package com.example.qimiao.zz.ui.activity.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.example.qimiao.zz.App.MyApplication
import com.example.qimiao.zz.R
import com.example.qimiao.zz.mvp.m.bean.LoginToken
import com.example.qimiao.zz.mvp.m.bean.RefreshTokenBean
import com.example.qimiao.zz.mvp.m.bean.ResultCode
import com.example.qimiao.zz.mvp.p.ParsingPresenter
import com.example.qimiao.zz.ui.activity.HomeActivity
import com.example.qimiao.zz.ui.activity.base.BaseActivity
import com.example.qimiao.zz.uitls.Constant
import com.example.urilslibrary.SharedPreferencesUtil
import com.gyf.immersionbar.ImmersionBar

class SplashActivity : BaseActivity() {
//    private val a = arrayOfNulls<Any>(1)
    private var mPresenter: ParsingPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }


    override fun initview(): Activity {
        ImmersionBar.with(this).fullScreen(true).init()
        mPresenter = ParsingPresenter(this)
        var access_token = SharedPreferencesUtil.getString(this, "access_token", "")

        if (access_token == null || "" == access_token) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            Constant.access_token = "Bearer " + access_token
            mPresenter?.start<RefreshTokenBean>("checkToken", "checkToken", "", hashMapOf<String, Any>(), access_token)
        }


        return this
    }

    override fun <T> setData(type: String, bean: T) {
        if ("checkToken" == type) {
            var data = bean as RefreshTokenBean

            if (data != null) {
                var time = data.exp
                if (time - 10000 <= 0) {
                    var refresh_token = SharedPreferencesUtil.getString(this, "refresh_token", "")
                    val mMap: HashMap<String, Any> = hashMapOf("refresh_token" to refresh_token)

                    mPresenter?.start<ResultCode>("refreshToken", "refreshToken", "", mMap)
                } else {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
            }
        } else {
            var data = bean as LoginToken
            if (data != null) {
                SharedPreferencesUtil.saveString(MyApplication.getAppContext(), "refresh_token", data.refresh_token)
                SharedPreferencesUtil.saveString(MyApplication.getAppContext(), "access_token", data.access_token)
                "Bearer "+
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }

        }
    }

    override fun onStop() {
        var access_token = SharedPreferencesUtil.getString(this, "access_token", "")
        Constant.access_token = "Bearer " + access_token
        super.onStop()
    }

    override fun onError(type: String, error: Throwable) {
//        startActivitya(LoginActivity::class.java)
        if ("checkToken" == type) {
            var refresh_token = SharedPreferencesUtil.getString(this, "refresh_token", "")
            val mMap: HashMap<String, Any> = hashMapOf("refresh_token" to refresh_token)
            mPresenter?.start<ResultCode>("refreshToken", "refreshToken", "", mMap)

        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
