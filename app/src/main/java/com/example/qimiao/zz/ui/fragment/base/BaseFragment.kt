package com.example.qimiao.zz.ui.fragment.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import com.example.qimiao.zz.mvp.v.Contract
import com.umeng.analytics.MobclickAgent
import com.umeng.message.PushAgent


abstract class BaseFragment : Fragment(), Contract.View {
    var isFirst: Boolean = false
    var rootView: View? = null
    var isFragmentVisiable: Boolean = false
    var mContext: Fragment? = this
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = getLayoutView()
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        mContext = initView()
        if (mContext != null) {
            PushAgent.getInstance(mContext?.context).onAppStart()
        }

    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            isFragmentVisiable = true
        }
        if (rootView == null) {
            return
        }
        //可见，并且没有加载过
        if (!isFirst && isFragmentVisiable) {
            onFragmentVisiableChange(true)
            return
        }
        //由可见——>不可见 已经加载过
        if (isFragmentVisiable) {
            onFragmentVisiableChange(false)
            isFragmentVisiable = false
        }
    }

    open protected fun onFragmentVisiableChange(b: Boolean) {

    }


    //    abstract fun getLayoutResources(): Int
    abstract fun getLayoutView(): View

    abstract fun initView(): Fragment

    override fun onResume() {
        super.onResume()
        if (mContext != null) {
            MobclickAgent.onResume(mContext?.context)
        }

    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(mContext?.context)
    }

    override fun onDestroy() {
        super.onDestroy()
        rootView = null
    }
}