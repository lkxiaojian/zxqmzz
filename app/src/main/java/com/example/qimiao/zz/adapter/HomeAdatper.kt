package com.example.qimiao.zz.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.example.qimiao.kotlinframework.mvp.m.bean.HomeBean
import com.example.qimiao.kotlinframework.utils.ImageLoadUtils
import com.example.qimiao.zz.R
import com.example.qimiao.zz.ui.activity.VideoPlayerActivity


/**
 * Created by lk on 2018/6/8.
 */
class HomeAdatper(context: Context, list: MutableList<HomeBean.IssueListBean.ItemListBean>?) : RecyclerView.Adapter<HomeAdatper.HomeViewHolder>() {




    var context: Context? = null
    var list: MutableList<HomeBean.IssueListBean.ItemListBean>? = null
    var inflater: LayoutInflater? = null

    init {
        this.context = context
        this.list = list
        this.inflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(inflater?.inflate(R.layout.item_home, parent, false), context!!)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        var bean = list?.get(position)
        var title = bean?.data?.title
        var category = bean?.data?.category
        var minute = bean?.data?.duration?.div(60)
        var second = bean?.data?.duration?.minus((minute?.times(60)) as Long)
        var realMinute: String
        var realSecond: String
        if (minute!! < 10) {
            realMinute = "0" + minute
        } else {
            realMinute = minute.toString()
        }
        if (second!! < 10) {
            realSecond = "0" + second
        } else {
            realSecond = second.toString()
        }
        var playUrl = bean?.data?.playUrl
        var photo = bean?.data?.cover?.feed
        var author = bean?.data?.author
        ImageLoadUtils.display(context!!, holder?.iv_photo, photo as String)
        holder?.tv_title?.text = title
        holder?.tv_detail?.text = "发布于 $category / $realMinute:$realSecond"
        if (author != null) {
            ImageLoadUtils.display(context!!, holder?.iv_user, author.icon as String)
        } else {
            holder?.iv_user?.visibility = View.GONE
        }
        holder?.rl_all?.setOnClickListener {
            var intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putExtra("bean", bean)
            context!!.startActivity(intent)


        }

    }

    class HomeViewHolder(itemView: View?, context: Context) : RecyclerView.ViewHolder(itemView) {
        var tv_detail: TextView? = null
        var tv_title: TextView? = null
        var iv_photo: ImageView? = null
        var iv_user: ImageView? = null
        var rl_all: RelativeLayout? = null

        init {
            tv_detail = itemView?.findViewById(R.id.tv_detail)
            tv_title = itemView?.findViewById(R.id.tv_title)
            iv_photo = itemView?.findViewById(R.id.iv_photo)
            iv_user = itemView?.findViewById(R.id.iv_user)
            rl_all = itemView?.findViewById(R.id.rl_all)

        }
    }


}