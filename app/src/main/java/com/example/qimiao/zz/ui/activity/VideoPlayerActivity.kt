package com.example.qimiao.zz.ui.activity

import android.app.Activity
import android.os.Bundle
import com.example.qimiao.kotlinframework.mvp.m.bean.HomeBean
import com.example.qimiao.kotlinframework.utils.ImageLoadUtils
import com.example.qimiao.zz.R
import com.example.qimiao.zz.ui.activity.base.BaseActivity
import com.lk.playvideolibrary.NiceVideoPlayer
import com.lk.playvideolibrary.NiceVideoPlayerManager
import com.lk.playvideolibrary.TxVideoPlayerController
import kotlinx.android.synthetic.main.videoplayeractivity.*

/**
 * Created by lk on 2018/6/19.
 */
class VideoPlayerActivity : BaseActivity() {

    override fun onError(type: String, error: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var bean: HomeBean.IssueListBean.ItemListBean? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.videoplayeractivity)
    }

    override fun <T> setData(type: String, bean: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initview(): Activity {
        bean = intent.extras.get("bean") as HomeBean.IssueListBean.ItemListBean?
        var title = bean?.data?.title
        var playUrl = bean?.data?.playUrl
        var minute = bean?.data?.duration?.div(60) as Long
        var second = bean?.data?.duration?.minus((minute?.times(60)) as Long) as Long
        var photo = bean?.data?.cover?.feed
        nice_video_player.setPlayerType(NiceVideoPlayer.TYPE_IJK)
        nice_video_player.setUp(playUrl, null)
        val controller = TxVideoPlayerController(this)
        controller.setImage(R.mipmap.videobag)
        controller.setTitle(title)
        controller.setLenght(minute * 60 * 1000 + second*1000)
        ImageLoadUtils.display(this, controller.imageView(), photo as String)
        nice_video_player.setController(controller)
        return this
    }


    override fun onStop() {
        super.onStop()
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
    }

    override fun onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return
        super.onBackPressed()
    }
}