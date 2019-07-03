package com.example.qimiao.zz.mvp.m.bean

import android.content.Intent

data class ImageResult(
        val requestCode: Int,
        val resultCode: Int,
        val data: Intent
)