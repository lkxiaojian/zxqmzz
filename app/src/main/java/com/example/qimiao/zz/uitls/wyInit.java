//package com.example.qimiao.zz.uitls;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.util.Log;
//import com.netease.nimlib.sdk.SDKOptions;
//import com.netease.nimlib.sdk.StatusBarNotificationConfig;
//import com.netease.nimlib.sdk.auth.LoginInfo;
//import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
//import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
//import com.netease.nimlib.sdk.uinfo.model.UserInfo;
//import com.umeng.message.inapp.UmengSplashMessageActivity;
//public class wyInit {
//
//    // 如果返回值为 null，则全部使用默认参数。
//    public SDKOptions options(Context context) {
//        SDKOptions options = new SDKOptions();
//
//        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
//        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
//        config.notificationEntrance = UmengSplashMessageActivity.class; // 点击通知栏跳转到该Activity
////        config.notificationSmallIconId = R.drawable.ic_stat_notify_msg;
//        // 呼吸灯配置
//        config.ledARGB = Color.GREEN;
//        config.ledOnMs = 1000;
//        config.ledOffMs = 1500;
//        // 通知铃声的uri字符串
//        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
//        options.statusBarNotificationConfig = config;
//
//        // 配置是否需要预下载附件缩略图，默认为 true
//        options.preloadAttach = true;
//
//        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
//        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
//        options.thumbnailSize = 480/ 2;
//
//        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
//        options.userInfoProvider = new UserInfoProvider() {
//            @Override
//            public UserInfo getUserInfo(String account) {
//                Log.e("tag","account--"+account);
//
//                return null;
//            }
//
//            @Override
//            public String getDisplayNameForMessageNotifier(String account, String sessionId,
//                                                           SessionTypeEnum sessionType) {
//                Log.e("tag","sessionId--"+sessionId);
//                return null;
//            }
//
//            @Override
//            public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionType, String sessionId) {
//                Log.e("tag","sessionType--"+sessionType.getValue());
//                return null;
//            }
//        };
//        return options;
//    }
//
//    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
//    public LoginInfo loginInfo() {
//        return null;
//    }
//
//}
