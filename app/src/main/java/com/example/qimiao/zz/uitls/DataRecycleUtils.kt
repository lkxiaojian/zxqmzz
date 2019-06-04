package com.example.qimiao.zz.uitls

import android.databinding.BindingAdapter
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.qimiao.kotlinframework.adapter.base.BaseAdapterIml
import com.example.qimiao.zz.R
import com.example.qimiao.zz.adapter.TestMoreTypeAdapter
import com.example.qimiao.zz.mvp.m.bean.FindBean

/**
 * Created by lk on 2018/6/11.
 */

class DataRecycleUtils {
    companion object {
        /**
         * 单类型使用样例，不需要重写方法（BaseAdapterIml）
         */
        @BindingAdapter("find")
        @JvmStatic
        fun setfindadapter(recyclerView: RecyclerView, data: MutableList<FindBean>?) {
            if (data == null) {
                return
            }
            recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 2)
            val layout = R.layout.find_item
            val adapter = BaseAdapterIml(recyclerView.context, data, layout, arrayOf())
            recyclerView.adapter = adapter
        }

        /**
         * 多类型使用样例（TestMoreTypeAdapter 继承 AbsBaseAdapterIml 重写方法）
         */

        @BindingAdapter("test")
        @JvmStatic
        fun settestadapter(recyclerView: RecyclerView, data: MutableList<FindBean>?) {
            if (data == null) {
                return
            }
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            val layout1 = R.layout.find_item
            val layout2 = R.layout.find_more_item

            val adapter = TestMoreTypeAdapter(recyclerView.context, data, 0, arrayOf(layout1, layout2))
            recyclerView.adapter = adapter
        }
    }

}
