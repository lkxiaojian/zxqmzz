package com.example.qimiao.zz.ui.activity.login

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.qimiao.zz.R
import com.example.qimiao.zz.ui.activity.base.BaseActivity
import com.example.qimiao.zz.uitls.ToolbarUtils
import kotlinx.android.synthetic.main.toolbar_layout.*

class DealActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deal)
    }

    override fun initview(): Activity {
        ToolbarUtils.setToolTitle(toolBar, this, "找回密码", null, "", 0)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        return this
    }

    override fun <T> setData(type: String, bean: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(type: String, error: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        finish()
        return super.onOptionsItemSelected(item)
    }
}
