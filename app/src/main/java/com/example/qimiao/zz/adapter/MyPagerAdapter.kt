package com.example.qimiao.zz.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.ArrayList

class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var mFragments: ArrayList<Fragment>? = null
    var mTitles: Array<String>? = null
    fun MyPagerAdapter(mFragments: ArrayList<Fragment>, mTitles: Array<String>) {
        this.mFragments = mFragments
        this.mTitles = mTitles

    }

    override fun getCount(): Int {
        if (mFragments != null) {
            return mFragments!!.size
        }
        return 0

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles!![position]
    }

    override fun getItem(position: Int): Fragment {

        return mFragments!![position]

    }
}