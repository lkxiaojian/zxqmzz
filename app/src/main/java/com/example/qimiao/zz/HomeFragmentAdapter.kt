package com.example.qimiao.zz

import android.content.Context
import android.util.Log
import com.example.qimiao.kotlinframework.viewholder.BindingHolder
import com.example.qimiao.zz.adapter.GlideImageLoader
import com.example.qimiao.zz.adapter.base.AbsBaseAdapterIml
import com.youth.banner.Banner
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.transformer.CubeOutTransformer

class HomeFragmentAdapter<T>(context: Context, var list: MutableList<T>, layout: Int, layoutIdList: Array<Int>) : AbsBaseAdapterIml<T>(context, list, layout, layoutIdList), OnBannerListener {


    override fun convert(holder: BindingHolder, t: T, position: Int) {
        if (position == 0) {
            var banner = holder.binding.root.findViewById<Banner>(R.id.banner)
            setBanner(banner)
        }
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
            return if (position == 0) {
                0
            } else {
                1
            }
        }
        return super.getItemViewType(position)
    }

    private fun setBanner(banner: Banner) {
        var ImageList = arrayListOf<String>()
        ImageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg")
        ImageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg")
        ImageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg")
        ImageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg")
        banner.setImages(ImageList).setImageLoader(GlideImageLoader()).setOnBannerListener(this).setBannerAnimation(CubeOutTransformer::class.java).start()
    }

    override fun OnBannerClick(position: Int) {
        Log.e("tag", "position--" + position)
    }


}