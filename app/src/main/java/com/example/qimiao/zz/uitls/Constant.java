package com.example.qimiao.zz.uitls;

import okhttp3.MediaType;

public class Constant {
    //设备号
    public static String deviceToken = "";

    //设备号
    public static String access_token = "";

    public static String header_token = "";
    //  身份证 证明 相机
    public final static int CAMERA_CODE_ID_Z = 1001;
    // 身份证反
    public final static int CAMERA_CODE_ID_F = 1002;
    //  身份证 证明 相册
    public final static int ALBUN_CODE_ID_Z = 1003;
    // 身份证反
    public final static int ALBUN_CODE_ID_F = 1004;
    //  营业执照 正 相册
    public final static int CAMERA_CODE_ID_ZY = 1005;
    // 营业执照 反
    public final static int ALBUN_CODE_ID_FY = 1006;

    public static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
    public static final MediaType MEDIA_TYPE_MP4 = MediaType.parse("video/mp4");
}
