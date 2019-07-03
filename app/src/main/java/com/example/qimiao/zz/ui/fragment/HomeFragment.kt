package com.example.qimiao.zz.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qimiao.kotlinframework.mvp.m.bean.HomeBean
import com.example.qimiao.zz.R
import com.example.qimiao.zz.adapter.HomeAdatper
import com.example.qimiao.zz.mvp.p.ParsingPresenter
import com.example.qimiao.zz.ui.fragment.base.BaseFragment
import com.example.urilslibrary.Utils
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import java.util.regex.Pattern

/**
 * Created by lk on 2018/6/12.
 */
class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    override fun onError(type: String, error: Throwable) {

    }
    var TAG = "HomeFragment"
    var mIsRefresh: Boolean = false
    var mPresenter: ParsingPresenter? = null
    var mList = ArrayList<HomeBean.IssueListBean.ItemListBean>()
    var mAdapter: HomeAdatper? = null
    var data: String? = null
    var bean: HomeBean? = null
    override fun <T> setData(type: String, reuslt: T) {
        if (!"loadData".equals(type)) {
            return
        }
        bean = reuslt as HomeBean
        val regEx = "[^0-9]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(bean?.nextPageUrl)
        data = m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()
        if (mIsRefresh) {
            mIsRefresh = false
            refreshLayout.isRefreshing = false
            if (mList.size > 0) {
                mList.clear()
            }

        }
        bean?.issueList!!
                .flatMap { it.itemList!! }
                .filter { it.type.equals("video") }
                .forEach { mList.add(it) }
        mAdapter?.notifyDataSetChanged()
    }

    override fun onRefresh() {
        if (!mIsRefresh) {
            mIsRefresh = true
            mPresenter?.start<HomeBean>("loadData", "loadData")
        }
    }

    var homeview: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeview = inflater.inflate(R.layout.fragment_home, container, false)
        return homeview
    }

    override fun getLayoutView(): View {
        return homeview!!
    }

    override fun initView() : Fragment {
        mPresenter = activity?.let { ParsingPresenter(this) }
        mPresenter?.start<HomeBean>("loadData", "loadData")
        recyclerView.layoutManager = LinearLayoutManager(activity)
        mAdapter = activity?.let { HomeAdatper(it, mList) }
        recyclerView.adapter = mAdapter
        refreshLayout.setOnRefreshListener(this)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var layoutManager: LinearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
                var lastPositon = layoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPositon == mList.size - 1) {
                    if (data != null) {
                        mPresenter?.moreData<HomeBean>(data, "", "loadData", null)
                    }

                }
            }
        })
        return  this
    }


}