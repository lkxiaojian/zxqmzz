package com.example.qimiao.zz.ui.activity.base

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import com.example.qimiao.zz.mvp.v.Contract
import com.umeng.analytics.MobclickAgent
import com.umeng.message.PushAgent

/**
 * Created by lk on 2018/6/19.
 */
abstract class BaseActivity : AppCompatActivity(), Contract.View {
    var mContext: Activity? = this
    override fun onStart() {
        super.onStart()
        mContext = initview()
        if (mContext != null) {
            PushAgent.getInstance(mContext).onAppStart()
        }
//        Log.e("tag--->", activity.localClassName)
    }

    override fun onResume() {
        super.onResume()
        if (mContext != null) {
            MobclickAgent.onPageStart(mContext?.localClassName.toString())
            MobclickAgent.onResume(mContext)
        }

    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPageEnd(mContext?.localClassName.toString())
        MobclickAgent.onPause(mContext)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    abstract fun initview(): Activity
}