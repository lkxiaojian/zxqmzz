package com.example.qimiao.zz.adapter

import android.content.Context
import com.example.qimiao.kotlinframework.viewholder.BindingHolder
import com.example.qimiao.zz.adapter.base.AbsBaseAdapterIml
import com.example.qimiao.zz.mvp.m.bean.FindBean

class TestMoreTypeAdapter<T>(context: Context, var list: MutableList<T>, layout: Int, layoutIdList: Array<Int>) : AbsBaseAdapterIml<T>(context, list, layout, layoutIdList) {
    override fun convert(holder: BindingHolder, t: T, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    override val layoutId: Int
        get() = layout
    override val layoutIds: Array<Int>
        get() = layoutIdList

    override fun isEnabled(viewType: Int): Boolean {
        return false
    }



    override fun getItemViewType(position: Int): Int {
        if (layoutIdList.size > 1) {
            return (list as MutableList<FindBean>)[position].laoutType

        }
        return super.getItemViewType(position)
    }

}