package com.example.qimiao.zz.ui.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qimiao.zz.R
import com.example.qimiao.zz.adapter.GlideImageLoader
import com.example.qimiao.zz.databinding.FragmentHometBinding
import com.example.qimiao.zz.mvp.p.ParsingPresenter
import com.example.qimiao.zz.ui.fragment.base.BaseFragment
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.transformer.CubeOutTransformer
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_homet.*
import java.util.ArrayList

/**
 * Created by lk on 2018/6/12.
 */
class HomeTFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, OnBannerListener {
    override fun OnBannerClick(position: Int) {
        Log.e("tag","position--"+position)
    }

    override fun onRefresh() {
        srl_homet.isRefreshing = false
    }

    override fun onError(type: String, error: Throwable) {

    }

    var mPresenter: ParsingPresenter? = null
    var mList: MutableList<String>? = null
    var binding: FragmentHometBinding? = null
    override fun <T> setData(type: String, bean: T) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.fragment_homet, container, false) as FragmentHometBinding
        return binding?.root
    }

    override fun getLayoutView(): View {
        return binding!!.root
    }

    override fun initView(): Fragment {

        srl_homet.setOnRefreshListener(this)
        mPresenter = ParsingPresenter(this)
        mList = arrayListOf<String>()
        for (i in 1..10) {
            mList?.add(i.toString())
        }
        binding?.data = mList as ArrayList<String>
        return this
    }



}

