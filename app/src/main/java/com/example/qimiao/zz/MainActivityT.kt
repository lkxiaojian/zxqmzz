package com.example.qimiao.zz

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.example.qimiao.zz.App.MyApplication
import com.example.qimiao.zz.adapter.MyPagerAdapter
import com.example.qimiao.zz.mvp.m.bean.SvgImage
import com.example.qimiao.zz.ui.activity.base.BaseActivity
import com.example.qimiao.zz.ui.fragment.FindFragment
import com.example.qimiao.zz.ui.fragment.HomeFragment
import com.example.qimiao.zz.ui.fragment.MineFragment
import com.example.qimiao.zz.ui.fragment.TestFragment
import com.example.urilslibrary.Utils
import com.flyco.tablayout.listener.OnTabSelectListener
import com.gyf.immersionbar.ImmersionBar
import com.taobao.agoo.BaseNotifyClickActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main_t.*
import kotlinx.android.synthetic.main.svg_mine.*
import java.util.*

class MainActivityT : BaseActivity(), OnTabSelectListener, AMapLocationListener {


    //    private val mContext = this
    private val mFragments = ArrayList<Fragment>()
    private val mTitles = arrayOf("首页", "發現", "我的", "测试")
    private var mAdapter: MyPagerAdapter? = null
    private val arrayA: Array<Int> = arrayOf(50, 43, 23, 79, 80, 1, 34, 22, 50)
    //声明AMapLocationClient类对象
    private var mLocationClient: AMapLocationClient? = null
    //声明AMapLocationClientOption对象
    var mLocationOption: AMapLocationClientOption? = null

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

    override fun initview(): Activity {
//        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).statusBarColor(R.color.white).fitsSystemWindows(true).init()
//        val window = window
//        val params = window.attributes
//        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//        window.attributes = params



//        ImmersionBar.with(this).statusBarColor(R.color.white)
//                .statusBarColorTransform(R.color.black)  //状态栏变色后的颜色
//                .navigationBarColorTransform(R.color.black)
//                .barColor(R.color.white)
//                .autoStatusBarDarkModeEnable(true,0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
//                .autoNavigationBarDarkModeEnable(true,0.2f)
//                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
//                .autoDarkModeEnable(true).init()
//        initFreament()
        requestPermission()
//        test()
        return this

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

    override fun onLocationChanged(amapLocation: AMapLocation?) {
        if (amapLocation?.errorCode == 0) {
            //可在其中解析amapLocation获取相应内容。
            Utils.ToastLong(MyApplication.getAppContext(), "定位获取成功")

        } else {
            //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
            Log.e("AmapError", "location Error, ErrCode:"
                    + amapLocation?.errorCode + ", errInfo:"
                    + amapLocation?.errorInfo)
//            Utils.ToastLong(MyApplication.getAppContext(), "location Error, ErrCode:"
//                    + amapLocation?.errorCode + ", errInfo:"
//                    + amapLocation?.errorInfo)
        }
    }

}
