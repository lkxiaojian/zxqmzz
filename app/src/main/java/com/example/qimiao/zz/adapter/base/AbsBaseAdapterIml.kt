package com.example.qimiao.zz.adapter.base

import android.content.Context
import com.example.qimiao.kotlinframework.adapter.base.BaseDataRecycleAdapter

/**
 * 需要重写个方法的继承这个类
 */
abstract class AbsBaseAdapterIml<T>(context: Context, list: MutableList<T>, var layout: Int, var layoutIdList: Array<Int>) :
        BaseDataRecycleAdapter<T>(context, list)