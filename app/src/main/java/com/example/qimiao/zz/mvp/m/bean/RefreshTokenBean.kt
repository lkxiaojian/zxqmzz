package com.example.qimiao.zz.mvp.m.bean

data class RefreshTokenBean(
    val active: Boolean,
    val aud: List<String>,
    val authorities: List<String>,
    val client_id: String,
    val exp: Int,
    val scope: List<String>,
    val user_name: String
)