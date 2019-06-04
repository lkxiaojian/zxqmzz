package com.example.qimiao.zz.ui.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qimiao.zz.R
import com.example.qimiao.zz.databinding.HotItemBinding
import com.example.qimiao.zz.mvp.m.bean.FindBean
import com.example.qimiao.zz.ui.fragment.base.BaseFragment
import java.util.ArrayList

/**
 * Created by lk on 2018/6/12.
 */
class HotFragment : BaseFragment() {
    var binding: HotItemBinding? = null
    var mList: MutableList<FindBean>? = arrayListOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.hot_item, container, false) as HotItemBinding
        return binding?.root
    }


    override fun onError(type: String, error: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> setData(type: String, bean: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun initView() : Fragment {
        for (i in 1..10){
            if(i>3){
                var bean= FindBean(i,"1"+i,"1","1","1","1","1",0)
                mList?.add(bean)
            }else{
                var bean= FindBean(i,"1"+i,"1","1","1","1","1",1)
                mList?.add(bean)
            }


        }

        binding?.data =mList as ArrayList<FindBean>
        return this
    }

    override fun getLayoutView(): View {
        return binding!!.root
    }

}