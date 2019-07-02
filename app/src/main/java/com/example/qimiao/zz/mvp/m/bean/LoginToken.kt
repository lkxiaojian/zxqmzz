package com.example.qimiao.zz.mvp.m.bean

data class LoginToken(
        val access_token: String,
        val expires_in: Int,
        val refresh_token: String,
        val scope: String,
        val token_type: String,
        val error: String,
        val error_description: String
        )