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
import com.example.qimiao.zz.mvp.m.bean.FindBean
import com.example.qimiao.zz.mvp.p.ParsingPresenter
import com.example.qimiao.zz.ui.fragment.base.BaseFragment
import java.util.ArrayList

/**
 * Created by lk on 2018/6/12.
 */
class FindFragment : BaseFragment() {
    override fun onError(type: String, error: Throwable) {

    }


    var mPresenter: ParsingPresenter? = null
    var mList: MutableList<FindBean>? = null
    var binding: FindHomeBinding? = null
    override fun <T> setData(type: String, bean: T) {
        if (!"findFragment".equals(type)) {
            return
        }
        mList = bean as MutableList<FindBean>
        binding?.data = mList as ArrayList<FindBean>

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.find_home, container, false) as FindHomeBinding
        return binding?.root
    }

    override fun getLayoutView(): View {
        return binding!!.root
    }

    override fun initView() : Fragment {
        mPresenter = ParsingPresenter(this)
        var map = HashMap<String, Any>()
        map.put("type", "findFragment")
        map.put("method", "FindData")
        mPresenter?.start<MutableList<FindBean>>(map)
        binding?.data = mList as ArrayList<FindBean>?
        return this
    }



}

