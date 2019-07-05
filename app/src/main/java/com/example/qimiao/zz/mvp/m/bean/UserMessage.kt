package com.example.qimiao.zz.mvp.m.bean


data class UserMessage(
    val `data`: User,
    val code: Int,
    val msg: String
)

data class User(
    val avatar: Any,
    val balance: Int,
    val phone: String,
    val rankPoints: Int,
    val username: String
)