package com.example.qimiao.zz.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.ArrayList

class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
var  mFragments= arrayListOf<Fragment>()
    var  mTitles:Array<String>?= null
   fun MyPagerAdapter( mFragments: ArrayList<Fragment>, mTitles: Array<String>){
      this. mFragments=mFragments
       this.mTitles=mTitles

}
    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles!![position]
    }

    override fun getItem(position: Int): Fragment {
        return mFragments.get(position)
    }
}