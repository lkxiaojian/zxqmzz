package com.example.qimiao.zz

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import com.example.qimiao.zz.adapter.MyPagerAdapter
import com.example.qimiao.zz.ui.activity.base.BaseActivity
import com.example.qimiao.zz.ui.fragment.FindFragment
import com.example.qimiao.zz.ui.fragment.HomeFragment
import com.example.qimiao.zz.ui.fragment.MineFragment
import com.example.qimiao.zz.ui.fragment.TestFragment
import com.flyco.tablayout.listener.OnTabSelectListener
import com.gyf.barlibrary.ImmersionBar
import com.taobao.agoo.BaseNotifyClickActivity
import kotlinx.android.synthetic.main.activity_main_t.*
import java.util.*

class MainActivityT : BaseActivity(), OnTabSelectListener {


//    private val mContext = this
    private val mFragments = ArrayList<Fragment>()
    private val mTitles = arrayOf("首页", "發現", "我的", "测试")
    private var mAdapter: MyPagerAdapter? = null
    private val arrayA: Array<Int> = arrayOf(50, 43, 23, 79, 80, 1, 34, 22, 50)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_t)
//        initview()

    }

    private fun initFreament() {
        mFragments.add(HomeFragment())
        mFragments.add(FindFragment())
        mFragments.add(MineFragment())
        mFragments.add(TestFragment())
//        val forName = Class.forName("")
//        mFragments.add(forName.newInstance()  as Fragment)
        mAdapter = MyPagerAdapter(supportFragmentManager)
        this.mAdapter!!.MyPagerAdapter(mFragments, mTitles)
        vp.adapter = mAdapter
        tl_9.setViewPager(vp)


    }

    override  fun initview(): Activity {
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).statusBarColor(R.color.black).fitsSystemWindows(true).init()
        val window = window
        val params = window.attributes
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.attributes = params
        initFreament()
//        test()
        return this

    }

    override fun onTabReselect(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTabSelect(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> setData(type: String, bean: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(type: String, error: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
