package com.example.qimiao.zz.ui.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qimiao.zz.R
import com.example.qimiao.zz.adapter.MyPagerAdapter
import com.example.qimiao.zz.databinding.TestMineVpBinding
import com.example.qimiao.zz.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main_t.tl_9
import kotlinx.android.synthetic.main.test_mine_vp.*
import java.util.ArrayList


/**
 * Created by lk on 2018/6/12.
 */
class TestFragment : BaseFragment() {

    private var mTitle: String? = null
    private  var binding: TestMineVpBinding? = null
    private var savedState: Bundle? = null
    private var mFragments = ArrayList<Fragment>()
    private  val mTitles = arrayOf("首页", "發現", "我的","测试")
    private var mAdapter: MyPagerAdapter? = null

    override fun onError(type: String, error: Throwable) {

    }

    override fun <T> setData(type: String, bean: T) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.test_mine_vp, container, false) as TestMineVpBinding
        return binding?.root
    }

    override fun getLayoutView(): View {

        return binding!!.root
    }

    override fun initView(): Fragment {
        mFragments = ArrayList<Fragment>()
        initFreament()
        return this
    }

    private fun initFreament() {
        mFragments.add(HomeFragment())
        mFragments.add(FindFragment())
        mFragments.add(MineFragment())
        mAdapter = MyPagerAdapter(childFragmentManager)
        this.mAdapter!!.MyPagerAdapter(mFragments, mTitles)
        vp22.adapter = mAdapter
        tl_9.setViewPager(vp22)
    }


}