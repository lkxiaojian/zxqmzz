package com.example.qimiao.zz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import com.example.qimiao.zz.adapter.MyPagerAdapter
import com.example.qimiao.zz.ui.fragment.FindFragment
import com.example.qimiao.zz.ui.fragment.HomeFragment
import com.example.qimiao.zz.ui.fragment.MineFragment
import com.flyco.tablayout.listener.OnTabSelectListener
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_main_t.*
import java.util.*

class MainActivityT : AppCompatActivity(), OnTabSelectListener {


    val mContext = this
    val mFragments = ArrayList<Fragment>()
    val mTitles = arrayOf("首页", "發現", "我的")
    var mAdapter: MyPagerAdapter? = null
    val arrayA: Array<Int> = arrayOf(50, 43, 23, 79, 80, 1, 34, 22, 50)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_t)
        initview()

    }

    private fun initFreament() {
        mFragments.add(HomeFragment())
        mFragments.add(FindFragment())
        mFragments.add(MineFragment())
//        val forName = Class.forName("")
//        mFragments.add(forName.newInstance()  as Fragment)
        mAdapter = MyPagerAdapter(supportFragmentManager)
        this.mAdapter!!.MyPagerAdapter(mFragments, mTitles)
        vp.adapter = mAdapter
        tl_9.setViewPager(vp)


    }


    private fun initview() {
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).statusBarColor(R.color.black).fitsSystemWindows(true).init()
        val window = window
        val params = window.attributes
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.attributes = params
        initFreament()
//        test()

    }

    override fun onTabReselect(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTabSelect(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 测试快速排序
     */
    fun test() {
        //定义一个数组A

        Log.e("TAG", Arrays.toString(arrayA))
        surtQuint(arrayA, 0, arrayA.size - 1)
        Log.e("TAG", Arrays.toString(arrayA))
    }


    fun surtQuint(array: Array<Int>, start: Int, end: Int) {
        if (start < end) {

//50, 43, 23, 79, 80, 1, 34, 22, 50
            var startIndex = start
            var endIndex = end
            val index = array[start]
            //退出循环条件
            while (startIndex < endIndex) {
                //左边的数值小于于右边的数字，让末尾的下标-1
                while (index <= array[endIndex] && startIndex < endIndex) {
                    endIndex--
                }
                array[startIndex] = array[endIndex]

                //左边的数值大于右边的的数值
                while (index >= array[startIndex] && startIndex < endIndex) {
                    startIndex++
                }
                array[endIndex] = array[startIndex]
            }
            array[startIndex] = index
            surtQuint(array, start, startIndex)
            surtQuint(array, startIndex + 1, end)
        }
    }
}
