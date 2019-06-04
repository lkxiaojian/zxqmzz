package com.example.qimiao.zz

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.example.qimiao.zz.ui.fragment.HomeFragment
import com.example.qimiao.zz.ui.fragment.HotFragment
import com.example.qimiao.zz.ui.fragment.MineFragment
import com.example.qimiao.zz.ui.fragment.FindFragment
import com.example.qimiao.zz.uitls.showToast
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_main.*
import com.xuexiang.xupdate.XUpdate
import com.tbruyelle.rxpermissions2.RxPermissions


class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        clearState()
        when (v?.id) {
            R.id.rb_find -> {
                rb_find.isChecked = true
                rb_find.setTextColor(ContextCompat.getColor(this,R.color.black))
                supportFragmentManager.beginTransaction().show(findFragment)
                        .hide(homeFragment)
                        .hide(mineFragment)
                        .hide(hotFragemnt)
                        .commit()

            }
            R.id.rb_home -> {
                rb_home.isChecked = true
                rb_home.setTextColor(ContextCompat.getColor(this,R.color.black))
                supportFragmentManager.beginTransaction().show(homeFragment)
                        .hide(findFragment)
                        .hide(mineFragment)
                        .hide(hotFragemnt)
                        .commit()

            }
            R.id.rb_hot -> {
                rb_hot.isChecked = true
                rb_hot.setTextColor(ContextCompat.getColor(this,R.color.black))
                supportFragmentManager.beginTransaction().show(hotFragemnt)
                        .hide(findFragment)
                        .hide(mineFragment)
                        .hide(homeFragment)
                        .commit()

            }
            R.id.rb_mine -> {
                rb_mine.isChecked = true
                rb_mine.setTextColor(ContextCompat.getColor(this,R.color.black))
                supportFragmentManager.beginTransaction().show(mineFragment)
                        .hide(findFragment)
                        .hide(homeFragment)
                        .hide(hotFragemnt)
                        .commit()
            }
        }
    }

    var homeFragment: HomeFragment? = null
    var findFragment: FindFragment? = null
    var hotFragemnt: HotFragment? = null
    var mineFragment: MineFragment? = null
    var toast: Toast? = null
    var mExitTime: Long = 0
     var mUpdateUrl = "https://raw.githubusercontent.com/xuexiangjys/XUpdate/master/jsonapi/update_test.json"
    val rxPermissions = RxPermissions(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initview()
        initFragment(savedInstanceState)
        //权限使用
        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                    } else {
                        // Oups permission denied
                    }
                }
    }

    private fun initFragment(savedInstanceState: Bundle?) {

        if (savedInstanceState != null) {
            //异常情况
            val mFragments: List<Fragment> = supportFragmentManager.fragments
            for (item in mFragments) {
                if (item is HomeFragment) {
                    homeFragment = item
                }
                if (item is FindFragment) {
                    findFragment = item
                }
                if (item is HotFragment) {
                    hotFragemnt = item
                }
                if (item is MineFragment) {
                    mineFragment = item
                }
            }
        } else {
            homeFragment = HomeFragment()
            findFragment = FindFragment()
            mineFragment = MineFragment()
            hotFragemnt = HotFragment()
            val fragmentTrans = supportFragmentManager.beginTransaction()
            fragmentTrans.add(R.id.fl_content, homeFragment)
            fragmentTrans.add(R.id.fl_content, findFragment)
            fragmentTrans.add(R.id.fl_content, mineFragment)
            fragmentTrans.add(R.id.fl_content, hotFragemnt)
            fragmentTrans.commit()
        }
        supportFragmentManager.beginTransaction().show(homeFragment)
                .hide(findFragment)
                .hide(mineFragment)
                .hide(hotFragemnt)
                .commit()
    }

    /**
     * 4.4以上沉浸式以及bar的管理
     */
    private fun initview() {
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init()
        val window = window
        val params = window.attributes
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.attributes = params
        setRadioButton()
        XUpdate.newBuild(this)
                .updateUrl(mUpdateUrl)
                .supportBackgroundUpdate(true)
                .update()

    }



    fun setRadioButton() {
        rb_home.isChecked = true
        rb_home.setTextColor(ContextCompat.getColor(this,R.color.black))
        rb_home.setOnClickListener(this)
        rb_find.setOnClickListener(this)
        rb_hot.setOnClickListener(this)
        rb_mine.setOnClickListener(this)
    }

    fun clearState() {
        rg_root.clearCheck()
        rb_home.setTextColor(ContextCompat.getColor(this,R.color.gray))
        rb_mine.setTextColor(ContextCompat.getColor(this,R.color.gray))
        rb_hot.setTextColor(ContextCompat.getColor(this,R.color.gray))
        rb_find.setTextColor(ContextCompat.getColor(this,R.color.gray))
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 3000) {
                finish()
                toast!!.cancel()
            } else {
                mExitTime = System.currentTimeMillis()
                toast = showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}
