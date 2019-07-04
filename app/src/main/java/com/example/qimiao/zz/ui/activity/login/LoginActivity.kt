package com.example.qimiao.zz.ui.activity.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.example.qimiao.zz.App.MyApplication
import com.example.qimiao.zz.R
import com.example.qimiao.zz.mvp.m.bean.LoginToken
import com.example.qimiao.zz.mvp.m.bean.ResultCode
import com.example.qimiao.zz.mvp.p.ParsingPresenter
import com.example.qimiao.zz.mvp.p.RxTimerPresenter
import com.example.qimiao.zz.mvp.v.TimerView
import com.example.qimiao.zz.ui.activity.HomeActivity
import com.example.qimiao.zz.ui.activity.base.BaseActivity
import com.example.qimiao.zz.uitls.Constant
import com.example.qimiao.zz.uitls.ui.Density
import com.example.urilslibrary.SharedPreferencesUtil
import com.example.urilslibrary.Utils
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity(), TimerView<Any> {
    // false 密码 登入 true  验证码登入
    private var isCode = false
    private var mPresenter: ParsingPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Density.setDensity(application, this)
        setContentView(R.layout.activity_login)
    }

    private fun onClick() {
        tv_code.setOnClickListener {
            isCode = !isCode
            if (isCode) {
                tv_code.text = "密码登入"
                ll_password_login.visibility = View.GONE
                ll_code_login.visibility = View.VISIBLE
            } else {
                tv_code.text = "验证码登入"
                ll_password_login.visibility = View.VISIBLE
                ll_code_login.visibility = View.GONE
            }
        }

        bt_login.setOnClickListener {
            var phoneNum = et_phone_num.text.toString()
            var passWord = et_password.text.toString()
            var code = et_code.text.toString()
            if (!Utils.isMobileNO(phoneNum)) {
                Utils.ToastShort(MyApplication.getAppContext(), "请输入正确的手机号")
                return@setOnClickListener
            }

            if (isCode) {

                if (TextUtils.isEmpty(code)) {
                    Utils.ToastShort(MyApplication.getAppContext(), "验证码为空")
                    return@setOnClickListener
                }

                val mMap: HashMap<*, *> = hashMapOf("authen_type" to "sms", "username" to phoneNum, "password" to code)
                mPresenter?.start<LoginToken>("oauthToken", "oauthToken", "", mMap)


            } else {
                if (TextUtils.isEmpty(passWord)) {
                    Utils.ToastShort(MyApplication.getAppContext(), "密码为空")
                    return@setOnClickListener
                }
                val mMap: HashMap<*, *> = hashMapOf("authen_type" to "password", "username" to phoneNum, "password" to passWord)
                mPresenter?.start<LoginToken>("oauthToken", "oauthToken", "", mMap)
            }

        }

        tv_rigest.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        tv_forget_password.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)

        }

        bt_time.setOnClickListener {
            var phoneNum = et_phone_num.text.toString()
            if (!Utils.isMobileNO(phoneNum)) {
                Utils.ToastShort(MyApplication.getAppContext(), "请输入正确的手机号")
                return@setOnClickListener
            }

            val mMap: HashMap<String, Any> = hashMapOf("deviceId" to Constant.deviceToken, "phone" to phoneNum, "smsType" to "1")
            mPresenter?.start<ResultCode>("sendRegisterCode", "sendRegisterCode", "", mMap)


            val presenter = RxTimerPresenter(this)
            presenter.timer(10)
        }
    }


    override fun initview(): Activity {
        ImmersionBar.with(this).fullScreen(true).init()
        ll_password_login.visibility = View.VISIBLE
        ll_code_login.visibility = View.GONE
        mPresenter = ParsingPresenter(this)
        onClick()
        return this
    }

    override fun <T> setData(type: String, bean: T) {
        if ("oauthToken" == type) {
            var data = bean as LoginToken
            SharedPreferencesUtil.saveString(MyApplication.getAppContext(), "refresh_token", data.refresh_token)
            SharedPreferencesUtil.saveString(MyApplication.getAppContext(), "access_token", data.access_token)
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }


    override fun onError(type: String, error: Throwable) {
        if (type == "sendRegisterCode") {
            Utils.ToastShort(MyApplication.getAppContext(), "验证码发送失败")
            bt_time.text = "获取验证码"
            bt_time.isEnabled = true
        } else if (type == "oauthToken") {
            Utils.ToastShort(MyApplication.getAppContext(), "用户名密码错误")
        }

    }


    override fun onCompile() {
        bt_time.text = "获取验证码"
        bt_time.isEnabled = true
    }

    override fun onRefresh(message: Any?) {
        bt_time.text = message.toString() + "秒"
    }

    override fun onError(message: Any?) {
        bt_time.text = "获取验证码"
        bt_time.isEnabled = true
    }

    override fun onBegin(message: Any?) {
        bt_time.isEnabled = false
        bt_time.text = message.toString() + "秒"
    }
}