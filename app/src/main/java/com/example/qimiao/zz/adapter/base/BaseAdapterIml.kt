package com.example.qimiao.kotlinframework.adapter.base

import android.content.Context
import com.example.qimiao.kotlinframework.viewholder.BindingHolder

/**
 * Created by lk on 2018/6/8.
 * 不需要重写各种方法
 */
class BaseAdapterIml<T>(context: Context, list: MutableList<T>, var layout: Int,var layoutIdList: Array<Int>) :
        BaseDataRecycleAdapter<T>(context, list) {
    override fun convert(holder: BindingHolder, t: T) {

    }

    override fun isEnabled(viewType: Int): Boolean {
        return true
    }


    override val layoutIds: Array<Int>
        get() = layoutIdList

    override val layoutId: Int
        get() = layout

}
