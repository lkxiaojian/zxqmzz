package com.example.qimiao.zz.ui.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qimiao.zz.R
import com.example.qimiao.zz.databinding.FindHomeBinding
import com.example.qimiao.zz.databinding.SvgMineBinding
import com.example.qimiao.zz.mvp.m.bean.SvgImage
import com.example.qimiao.zz.ui.fragment.base.BaseFragment

/**
 * Created by lk on 2018/6/12.
 */
class MineFragment : BaseFragment() {

    private var mTitle: String? = null
    var binding: SvgMineBinding? = null

    override fun onError(type: String, error: Throwable) {

    }

    override fun <T> setData(type: String, bean: T) {

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.svg_mine, container, false) as SvgMineBinding
        return binding?.root
    }

    override fun getLayoutView(): View {

        return binding!!.root
    }

    override fun initView() : Fragment {
        binding?.data=SvgImage(true)
        return this
    }

}