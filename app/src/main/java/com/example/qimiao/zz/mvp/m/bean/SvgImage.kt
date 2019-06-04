package com.example.qimiao.zz.mvp.m.bean

import android.view.View
import com.example.urilslibrary.Utils

data class SvgImage (val isCheck:Boolean){


    fun Onclick(view: View) {
//        Utils.ToastShort(view.context,"1"+view.isSelected)
        view.isSelected=!view.isSelected
    }


}