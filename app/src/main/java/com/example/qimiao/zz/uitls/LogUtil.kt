package com.example.qimiao.zz.uitls

import android.util.Log


class LogUtil {
    /**
     * 截断输出日志
     * @param msg
     */
    companion object {
        fun e(tag: String,  msg: String) {
            if (tag == null || tag.length == 0
                    || msg == null || msg.length == 0)
                return
            var segmentSize = 3 * 1024
            var length = msg.length
            var message:String?=msg
            if (length <= segmentSize) {// 长度小于等于限制直接打印
                Log.e(tag, message)
            } else {
                while (msg.length > segmentSize) {// 循环分段打印日志
                    var logContent = msg.substring(0, segmentSize)
                    message= msg.replace(logContent, "")
                    Log.e(tag, logContent)
                }
                Log.e(tag, message)// 打印剩余日志
            }
        }
    }
}