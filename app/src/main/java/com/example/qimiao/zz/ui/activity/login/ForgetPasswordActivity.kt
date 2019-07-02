package com.example.qimiao.zz.ui.activity.login

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import com.example.qimiao.zz.App.MyApplication
import com.example.qimiao.zz.R
import com.example.qimiao.zz.mvp.m.bean.ResultCode
import com.example.qimiao.zz.mvp.p.ParsingPresenter
import com.example.qimiao.zz.mvp.p.RxTimerPresenter
import com.example.qimiao.zz.mvp.v.TimerView
import com.example.qimiao.zz.ui.activity.base.BaseActivity
import com.example.qimiao.zz.uitls.Constant
import com.example.qimiao.zz.uitls.ToolbarUtils
import com.example.urilslibrary.SharedPreferencesUtil
import com.example.urilslibrary.Utils
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_forget_password.bt_register_time
import kotlinx.android.synthetic.main.activity_forget_password.et_code
import kotlinx.android.synthetic.main.activity_forget_password.et_password
import kotlinx.android.synthetic.main.activity_forget_password.et_phone_num
import kotlinx.android.synthetic.main.activity_register.*

import kotlinx.android.synthetic.main.toolbar_layout.*

class ForgetPasswordActivity : BaseActivity(), TimerView<Any> {


    private var mPresenter: ParsingPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
    }

    override fun initview(): Activity {
        ToolbarUtils.setToolTitle(toolBar, this, "找回密码", null, "", 0)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mPresenter = this?.let { ParsingPresenter(this) }
        onClick()
        return this
    }

    private fun onClick() {
        bt_register_time.setOnClickListener {
            var phoneNum = et_phone_num.text.toString()
            if (!Utils.isMobileNO(phoneNum)) {
                Utils.ToastShort(MyApplication.getAppContext(), "请输入正确的手机号")
                return@setOnClickListener
            }

            val mMap: HashMap<String, Any> = hashMapOf("deviceId" to Constant.deviceToken, "phone" to phoneNum)

            mPresenter?.start<ResultCode>("sendRegisterCode", "sendRegisterCode", "", mMap)
        }



        bt_forget.setOnClickListener {
            var phoneNum = et_phone_num.text.toString()
            var passWord = et_password?.text.toString()
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



//            if (passwordSure != passWord) {
//                Utils.ToastShort(MyApplication.getAppContext(), "两次密码输入不一致")
//                return@setOnClickListener
//            }
            val mMap: HashMap<*, *> = hashMapOf("deviceId" to Constant.deviceToken, "phone" to phoneNum, "password" to passWord, "smsCode" to code)

            mPresenter?.start<ResultCode>("register", "retrievePassword", "", mMap)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
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
            Utils.ToastShort(MyApplication.getAppContext(), "重置成功")
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
