package com.example.qimiao.zz.ui.activity.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.qimiao.zz.App.MyApplication
import com.example.qimiao.zz.R
import com.example.qimiao.zz.ui.activity.base.BaseActivity
import com.example.qimiao.zz.uitls.newIntent
import com.example.qimiao.zz.uitls.ui.Density
import com.example.urilslibrary.Utils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    // false 密码 登入 true  验证码登入
    private var isCode = false

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

            if(isCode){

                if (TextUtils.isEmpty(code)) {
                    Utils.ToastShort(MyApplication.getAppContext(), "验证码为空")
                    return@setOnClickListener
                }

            }else{
                if (TextUtils.isEmpty(passWord)) {
                    Utils.ToastShort(MyApplication.getAppContext(), "密码为空")
                    return@setOnClickListener
                }
            }




        }

        tv_rigest.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }


    override fun initview(): Activity {
        ll_password_login.visibility = View.VISIBLE
        ll_code_login.visibility = View.GONE
        onClick()
        return this
    }

    override fun <T> setData(type: String, bean: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(type: String, error: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}