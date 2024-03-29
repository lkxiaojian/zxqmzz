package com.example.qimiao.zz.ui.activity.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.example.qimiao.zz.App.MyApplication
import com.example.qimiao.zz.R
import com.example.qimiao.zz.mvp.m.bean.ResultCode
import com.example.qimiao.zz.mvp.p.ParsingPresenter
import com.example.qimiao.zz.mvp.p.RxTimerPresenter
import com.example.qimiao.zz.mvp.v.TimerView
import com.example.qimiao.zz.ui.activity.base.BaseActivity
import com.example.qimiao.zz.uitls.Constant
import com.example.urilslibrary.SharedPreferencesUtil
import com.example.urilslibrary.Utils
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_login.bt_login
import kotlinx.android.synthetic.main.activity_login.et_code
import kotlinx.android.synthetic.main.activity_login.et_password
import kotlinx.android.synthetic.main.activity_login.et_phone_num
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.collections.HashMap

class RegisterActivity : BaseActivity(), TimerView<Any> {


    var mPresenter: ParsingPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    override fun initview(): Activity {
        mPresenter = this?.let { ParsingPresenter(this) }
        ImmersionBar.with(this).fullScreen(true).init()
        onClick()

        return this
    }

    private fun onClick() {
        bt_login.setOnClickListener {
            var phoneNum = et_phone_num.text.toString()
            var passWord = et_password.text.toString()
//            var passwordSure = et_password_sure.text.toString()

            var code = et_code.text.toString()
            if (!Utils.isMobileNO(phoneNum)) {
                Utils.ToastShort(MyApplication.getAppContext(), "请输入正确的手机号")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(code)) {
                Utils.ToastShort(MyApplication.getAppContext(), "验证码为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(passWord)) {
                Utils.ToastShort(MyApplication.getAppContext(), "密码为空")
                return@setOnClickListener
            }
            if (!rb_true.isChecked) {
                Utils.ToastShort(MyApplication.getAppContext(), "协议未同意")
                return@setOnClickListener
            }


//            if (passwordSure != passWord) {
//                Utils.ToastShort(MyApplication.getAppContext(), "两次密码输入不一致")
//                return@setOnClickListener
//            }
            val mMap: HashMap<*, *> = hashMapOf("deviceId" to Constant.deviceToken, "phone" to phoneNum, "password" to passWord, "smsCode" to code,"smsType" to "0")

            mPresenter?.start<ResultCode>("register", "registerCode", "", mMap)
        }



        tv_login.setOnClickListener { finish() }
        tv_deal.setOnClickListener {

            startActivity(Intent(this, DealActivity::class.java))

        }
        rb_true.setOnCheckedChangeListener { _, b ->
            Log.e("tag",""+b)
            rb_true.isChecked=b
        }

        bt_register_time.setOnClickListener {
            var phoneNum = et_phone_num.text.toString()
            if (!Utils.isMobileNO(phoneNum)) {
                Utils.ToastShort(MyApplication.getAppContext(), "请输入正确的手机号")
                return@setOnClickListener
            }

            val mMap: HashMap<String, Any> = hashMapOf("deviceId" to Constant.deviceToken, "phone" to phoneNum)

            mPresenter?.start<ResultCode>("sendRegisterCode", "sendRegisterCode", "", mMap)
        }
    }

    override fun <T> setData(type: String, bean: T) {
        if ("sendRegisterCode" == type) {
            var bean = bean as ResultCode

            if (bean.code == 0) {
                val presenter = RxTimerPresenter(this)
                presenter.timer(10)
                Utils.ToastShort(MyApplication.getAppContext(), "验证码发送成功")
            } else {
                Utils.ToastShort(MyApplication.getAppContext(), bean.msg)
            }
        } else if ("register" == type) {
            SharedPreferencesUtil.saveString(this,"phone", et_phone_num.text.toString())
            SharedPreferencesUtil.saveString(this,"password", et_password.text.toString())
            Utils.ToastShort(MyApplication.getAppContext(), "注册成功")
            finish()
        }


    }

    override fun onError(type: String, error: Throwable) {
        if (type == "sendRegisterCode") {
            Utils.ToastShort(MyApplication.getAppContext(), "验证码发送失败")
            bt_register_time.text = "获取验证码"
            bt_register_time.isEnabled = true
        } else if ("register" == type) {
            Utils.ToastShort(MyApplication.getAppContext(), "注册失败")
        }
    }


    override fun onCompile() {
        bt_register_time.text = "获取验证码"
        bt_register_time.isEnabled = true
    }

    override fun onRefresh(message: Any?) {
        bt_register_time.text = message.toString() + "秒"
    }

    override fun onError(message: Any?) {
        bt_register_time.text = "获取验证码"
        bt_register_time.isEnabled = true
    }

    override fun onBegin(message: Any?) {
        bt_register_time.isEnabled = false
        bt_register_time.text = message.toString() + "秒"
    }
}
