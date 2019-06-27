package com.example.qimiao.zz.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Loader
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.amap.api.location.AMapLocation
import com.amap.api.maps.TextureMapView
import com.example.qimiao.zz.R
import com.example.qimiao.zz.databinding.SvgMineBinding
import com.example.qimiao.zz.mvp.m.bean.SvgImage
import com.example.qimiao.zz.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.svg_mine.*
import com.amap.api.location.AMapLocationListener
import com.amap.api.location.AMapLocationClient
import com.example.qimiao.zz.App.MyApplication
import com.amap.api.location.AMapLocationClientOption
import com.example.qimiao.zz.uitls.TestSHA1
import com.example.urilslibrary.Utils
import com.tbruyelle.rxpermissions2.RxPermissions


/**
 * Created by lk on 2018/6/12.
 */
class MineFragment : BaseFragment(), AMapLocationListener {


    private var mTitle: String? = null
    var binding: SvgMineBinding? = null
    var savedState: Bundle? = null
    private var mMap: TextureMapView? = null
    //声明AMapLocationClient类对象
    var mLocationClient: AMapLocationClient? = null


    //声明AMapLocationClientOption对象
    var mLocationOption: AMapLocationClientOption? = null

    override fun onError(type: String, error: Throwable) {

    }

    override fun <T> setData(type: String, bean: T) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedState = savedInstanceState
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.svg_mine, container, false) as SvgMineBinding
        this.savedState = savedInstanceState
        return binding?.root
    }

    override fun getLayoutView(): View {
        return binding!!.root
    }

    override fun initView(): Fragment {
        requestPermission()
        return this
    }

    /**
     * 请求权限
     */
    @SuppressLint("CheckResult")
    private fun requestPermission() {
        RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).subscribe { granted ->
            if (granted) {
                binding?.data = SvgImage(true)
                mMap = binding?.ggMap
                gg_map.onCreate(savedState)
                var aMap = mMap?.map
                var mUiSettings = aMap?.uiSettings
                mUiSettings?.isZoomControlsEnabled = false
                getStation()
            }else{
                Utils.ToastShort(MyApplication.getAppContext(),"定位的权限被拒绝")

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
//        val sHA1 = TestSHA1.sHA1(MyApplication.getAppContext())
//        Log.e("AmapError", "location Error, sHA1:"
//                + sHA1)
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMap?.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMap?.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMap?.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMap?.onSaveInstanceState(outState)

    }

    //声明定位回调监听器
    override fun onLocationChanged(amapLocation: AMapLocation?) {
        if (amapLocation?.errorCode == 0) {
            //可在其中解析amapLocation获取相应内容。
            Utils.ToastLong(MyApplication.getAppContext(),"定位获取成功")

        } else {
            //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
            Log.e("AmapError", "location Error, ErrCode:"
                    + amapLocation?.errorCode + ", errInfo:"
                    + amapLocation?.errorInfo)
            Utils.ToastLong(MyApplication.getAppContext(), "location Error, ErrCode:"
                    + amapLocation?.errorCode + ", errInfo:"
                    + amapLocation?.errorInfo)
        }

    }


}