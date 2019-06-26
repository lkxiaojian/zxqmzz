package com.example.qimiao.zz.ui.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.maps.TextureMapView
import com.example.qimiao.zz.R
import com.example.qimiao.zz.databinding.SvgMineBinding
import com.example.qimiao.zz.mvp.m.bean.SvgImage
import com.example.qimiao.zz.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.svg_mine.*


/**
 * Created by lk on 2018/6/12.
 */
class MineFragment : BaseFragment() {

    private var mTitle: String? = null
    var binding: SvgMineBinding? = null
    var savedState: Bundle? = null
    private var mMap:TextureMapView ?=null
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
        binding?.data = SvgImage(true)
         mMap=binding?.ggMap
        gg_map.onCreate(savedState)
        var aMap = mMap?.map
       var mUiSettings = aMap?.uiSettings
        mUiSettings?.isZoomControlsEnabled=false
        return this
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

}