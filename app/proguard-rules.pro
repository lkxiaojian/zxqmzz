# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5
-ignorewarnings

# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses

# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
-verbose

# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers

# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify

# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses

# 避免混淆泛型
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*


#############################################
#
# Android开发中一些需要保留的公共部分
#
#############################################

# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService


# 保留support下的所有类及其内部类
-keep class android.support.** {*;}

# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# 保留R下面的资源
-keep class **.R$* {*;}

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# webView处理，项目中没有使用到webView忽略即可
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}


# gilde
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#-------------- okhttp3 -------------
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.{*;}

-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

#----------retrofit--------------
#-keepclassmembernames,allowobfuscation interface * {
#    @retrofit2.http.* <methods>;
#}
#-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#

-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**
-dontwarn javax.annotation.**

#----------- gson ----------------
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.qiancheng.carsmangersystem.**{*;}

#
#
#  项目
#
# bean 类保证不被混淆
-keep class com.example.qimiao.zz.mvp.m.bean.** { *;}
# 反射
-keep class com.calm.comm.lib.ble.BleApi$Companion{
    *;
}
-keep class com.example.qimiao.kotlinframework.mvp.** { *;}
# rxpermissions
-dontwarn  com.tbruyelle.rxpermissions.**
-keep class com.tbruyelle.rxpermissions.** { *;}
-dontwarn okio.**
-dontwarn javax.annotation.**

-keep class com.example.urilslibrary.** { *;}


# 视频播放器
-keep class com.lk.playvideolibrary.** { *;}
#ijkplayer
-keep class tv.danmaku.ijk.media.player.** {*;}
-keep class tv.danmaku.ijk.media.player.IjkMediaPlayer{*;}
-keep class tv.danmaku.ijk.media.player.ffmpeg.FFmpegApi{*;}

# 自定义recycleView
-keep class com.example.recycleviewlibrary.** { *;}
-dontwarn okio.**
-dontwarn javax.annotation.**

# 沉浸式
-keep class com.gyf.barlibrary.** { *;}



# 图片压缩
-keep class  com.netease.image.library.** { *;}


# rxpermissions
-dontwarn  com.flyco.tablayout.**
-keep class com.flyco.tablayout.** { *;}


#databinding
-keep class android.databinding.** { *; }

#EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }


 #---------------------------------webview------------------------------------
 -keepclassmembers class fqcn.of.javascript.interface.for.Webview {
    public *;
 }
 -keepclassmembers class * extends android.webkit.WebViewClient {
     public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
     public boolean *(android.webkit.WebView, java.lang.String);
 }
 -keepclassmembers class * extends android.webkit.WebViewClient {
     public void *(android.webkit.WebView, jav.lang.String);
 }
 # updataApp
 -keep class com.xuexiang.xupdate.entity.** { *; }



-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-keep public class javax.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}


-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class com.umeng.weixin.handler.**
-keep class com.umeng.weixin.handler.*
-keep class com.umeng.qq.handler.**
-keep class com.umeng.qq.handler.*
-keep class UMMoreHandler{*;}
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep class com.tencent.mm.sdk.** {
   *;
}
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}

-keep class com.twitter.** { *; }

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**
-keep class com.kakao.** {*;}
-dontwarn com.kakao.**
-keep public class com.umeng.com.umeng.soexample.R$*{
    public static final int *;
}
-keep public class com.linkedin.android.mobilesdk.R$*{
    public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 友盟 分享
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}
-keep class com.umeng.socialize.impl.ImageImpl {*;}
-keep class com.sina.** {*;}
-dontwarn com.sina.**
-keep class  com.alipay.share.sdk.** {
   *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
# 友盟 统计
-keep class com.linkedin.** { *; }
-keep class com.android.dingtalk.share.ddsharemodule.** { *; }


-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# 友盟混淆的坑,官网给的混淆还要加上下面这些（集成push ） 不然部分机型会闪退
-keep public class com.example.qimiao.zz.R$*{
  public static final int *;
  }

  -dontwarn com.taobao.**
  -dontwarn anet.channel.**
  -dontwarn anetwork.channel.**
  -dontwarn org.android.**
  -dontwarn org.apache.thrift.**
  -dontwarn com.xiaomi.**
  -dontwarn com.huawei.**

  -keep class com.taobao.** {*;}
  -keep class org.android.** {*;}
  -keep class anet.channel.** {*;}
  -keep class com.umeng.** {*;}
  -keep class com.xiaomi.** {*;}
  -keep class com.huawei.** {*;}
  -keep class org.apache.thrift.** {*;}

  -keep class com.alibaba.sdk.android.**{*;}
  -keep class com.ut.**{*;}
  -keep class com.ta.**{*;}



  #### 友盟分享

  -dontwarn com.google.android.maps.**
  -dontwarn android.webkit.WebView
  -dontwarn com.umeng.**
  -dontwarn com.tencent.weibo.sdk.**
  -dontwarn com.facebook.**
  -keep public class javax.**
  -keep public class android.webkit.**
  -dontwarn android.support.v4.**
  -keep enum com.facebook.**
  -keepattributes Exceptions,InnerClasses,Signature
  -keepattributes *Annotation*
  -keepattributes SourceFile,LineNumberTable

  -keep public interface com.facebook.**
  -keep public interface com.tencent.**
  -keep public interface com.umeng.socialize.**
  -keep public interface com.umeng.socialize.sensor.**
  -keep public interface com.umeng.scrshot.**

  -keep public class com.umeng.socialize.* {*;}


  -keep class com.facebook.**
  -keep class com.facebook.** { *; }
  -keep class com.umeng.scrshot.**
  -keep public class com.tencent.** {*;}
  -keep class com.umeng.socialize.sensor.**
  -keep class com.umeng.socialize.handler.**
  -keep class com.umeng.socialize.handler.*
  -keep class com.umeng.weixin.handler.**
  -keep class com.umeng.weixin.handler.*
  -keep class com.umeng.qq.handler.**
  -keep class com.umeng.qq.handler.*
  -keep class UMMoreHandler{*;}
  -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
  -keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
  -keep class im.yixin.sdk.api.YXMessage {*;}
  -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
  -keep class com.tencent.mm.sdk.** {
     *;
  }
  -keep class com.tencent.mm.opensdk.** {
     *;
  }
  -keep class com.tencent.wxop.** {
     *;
  }
  -keep class com.tencent.mm.sdk.** {
     *;
  }

  -keep class com.twitter.** { *; }

  -keep class com.tencent.** {*;}
  -dontwarn com.tencent.**
  -keep class com.kakao.** {*;}
  -dontwarn com.kakao.**
  -keep public class com.umeng.com.umeng.soexample.R$*{
      public static final int *;
  }
  -keep public class com.linkedin.android.mobilesdk.R$*{
      public static final int *;
  }
  -keepclassmembers enum * {
      public static **[] values();
      public static ** valueOf(java.lang.String);
  }

  -keep class com.tencent.open.TDialog$*
  -keep class com.tencent.open.TDialog$* {*;}
  -keep class com.tencent.open.PKDialog
  -keep class com.tencent.open.PKDialog {*;}
  -keep class com.tencent.open.PKDialog$*
  -keep class com.tencent.open.PKDialog$* {*;}
  -keep class com.umeng.socialize.impl.ImageImpl {*;}
  -keep class com.sina.** {*;}
  -dontwarn com.sina.**
  -keep class  com.alipay.share.sdk.** {
     *;
  }

  -keepnames class * implements android.os.Parcelable {
      public static final ** CREATOR;
  }

  -keep class com.linkedin.** { *; }
  -keep class com.android.dingtalk.share.ddsharemodule.** { *; }



#  ###网易及时通讯
#  -dontwarn com.netease.**
#  -keep class com.netease.** {*;}
#  #如果你使用全文检索插件，需要加入
#  -dontwarn org.apache.lucene.**
#  -keep class org.apache.lucene.** {*;}



  ##########高德地图##########
  #3D 地图 V5.0.0之后：
  -keep   class com.amap.api.maps.**{*;}
  -keep   class com.autonavi.**{*;}
  -keep   class com.amap.api.trace.**{*;}

#  定位
  -keep class com.amap.api.location.**{*;}
  -keep class com.amap.api.fence.**{*;}
  -keep class com.autonavi.aps.amapapi.model.**{*;}

##  搜索
  -keep   class com.amap.api.services.**{*;}

 # 2D地图
  -keep class com.amap.api.maps2d.**{*;}
  -keep class com.amap.api.mapcore2d.**{*;}

 # 导航
  -keep class com.amap.api.navi.**{*;}
  ##刘海屏
   -keep class com.gyf.immersionbar.* {*;}
   -dontwarn com.gyf.immersionbar.**

# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }