package com.example.qimiao.zz.network

import com.example.qimiao.zz.uitls.Constant
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticatorK() : Authenticator {
    override fun authenticate(route: Route, response: Response): Request? {
        return response.request().newBuilder()
                .addHeader("Authorization", Constant.access_token)
                .build()
    }
}