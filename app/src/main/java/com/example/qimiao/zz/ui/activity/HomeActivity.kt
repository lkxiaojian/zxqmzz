package com.example.qimiao.zz.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.example.qimiao.zz.App.MyApplication
import com.example.qimiao.zz.R
import com.example.qimiao.zz.adapter.MyPagerAdapter
import com.example.qimiao.zz.ui.activity.base.BaseActivity
import com.example.qimiao.zz.ui.fragment.*
import com.example.qimiao.zz.uitls.StatusBarUtil
import com.example.qimiao.zz.uitls.ToolbarUtils
import com.example.qimiao.zz.uitls.ui.UIUtils
import com.example.urilslibrary.Utils
import com.flyco.tablayout.listener.OnTabSelectListener
import com.gyf.immersionbar.ImmersionBar
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_home.*
import java.util.ArrayList


class HomeActivity : BaseActivity(), OnTabSelectListener, AMapLocationListener, Toolbar.OnMenuItemClickListener {
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_icon -> {
                Log.e("tag", "action_icon")
            }
            R.id.action_search -> {
                Log.e("tag", "action_search")
            }
        }
        return true
    }


    private val mFragments = ArrayList<Fragment>()
    private val mTitles = arrayOf("推荐兼职", "附近兼职", "简单兼职", "知识")
    private var mAdapter: MyPagerAdapter? = null
    //声明AMapLocationClient类对象
    private var mLocationClient: AMapLocationClient? = null
    //声明AMapLocationClientOption对象
    var mLocationOption: AMapLocationClientOption? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun <T> setData(type: String, bean: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(type: String, error: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationChanged(p0: AMapLocation?) {

    }

    /**
     * 请求权限
     */
    @SuppressLint("CheckResult")
    private fun requestPermission() {
        RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe { granted ->
            if (granted) {
                initFreament()
                getStation()
            } else {
                Utils.ToastShort(MyApplication.getAppContext(), "权限被拒绝")
            }
        }
    }

    private fun initFreament() {
        mFragments.add(HomeTFragment())
        mFragments.add(FindFragment())
        mFragments.add(MineFragment())
        mFragments.add(TestFragment())
        mAdapter = MyPagerAdapter(supportFragmentManager)
        this.mAdapter!!.MyPagerAdapter(mFragments, mTitles)
        vp_home.adapter = mAdapter
        tl_2.setViewPager(vp_home)
        tl_2.showMsg(0, 5)
        tl_2.setMsgMargin(0, 0f, 10f)
        tl_2.showMsg(3, 1)
        tl_2.setMsgMargin(3, 0f, 10f)
    }

    override fun initview(): Activity {

        UIUtils.getInstance(this)
        toolbar.overflowIcon = ContextCompat.getDrawable(this, R.drawable.icon_msg)
        ToolbarUtils.setMarqueeForToolbarTitleView(toolbar)
        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)
        toolbar.setOnMenuItemClickListener(this)
        //设置沉浸式，并且toolbar设置padding
        StatusBarUtil.setStateBar(this, toolbar)
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;//黑色

        ImmersionBar.with(this).statusBarColor(R.color.black)
                .statusBarColorTransform(R.color.black)  //状态栏变色后的颜色
                .navigationBarColorTransform(R.color.black)
                .barColor(R.color.black)
                .autoStatusBarDarkModeEnable(true, 0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
                .autoNavigationBarDarkModeEnable(true, 0.2f)
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .autoDarkModeEnable(true).init()

        requestPermission()
        onClick()
        return this
    }

    private fun onClick() {


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_homet, menu)
        if (menu != null) {
            if (menu.javaClass == MenuBuilder::class.java) {
                try {
                    val m = menu.javaClass.getDeclaredMethod("setOptionalIconsVisible", java.lang.Boolean.TYPE)
                    m.isAccessible = true
                    m.invoke(menu, true)
                } catch (e: Exception) {
                }

            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        Log.e("tag", "toolbar")
        return super.onOptionsItemSelected(item)
    }

    private fun getStation() {
        //初始化定位
        mLocationClient = AMapLocationClient(MyApplication.getAppContext())
        //设置定位回调监听
        mLocationClient!!.setLocationListener(this)
        //初始化AMapLocationClientOption对象
        mLocationOption = AMapLocationClientOption()

//该方法默认为false。
        mLocationOption?.isOnceLocation = true

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption?.isOnceLocationLatest = true
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption?.isNeedAddress = true
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption?.isMockEnable = true
        //给定位客户端对象设置定位参数
        mLocationClient?.setLocationOption(mLocationOption)
        //启动定位
        mLocationClient?.startLocation()
    }

    override fun onTabSelect(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTabReselect(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
