package com.example.qimiao.zz.uitls;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.qimiao.zz.uitls.ui.UIUtils;

import java.util.Objects;


public class StatusBarUtil {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setTranslateStateBar(Activity activity, Object... objects) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            if (objects == null) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//黑色
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置透明状态栏,这样才能让 ContentView 向上
            if (objects == null) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }

        }
    }

    public static void setStateBar(Activity activity, View toolBar, Object... objects) {
        setTranslateStateBar(activity, objects);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP&&objects == null) {
            if (toolBar != null) {
                toolBar.setPadding(toolBar.getPaddingLeft(), UIUtils.getInstance().getSystemBarHeight(activity),
                        toolBar.getPaddingRight(), toolBar.getPaddingBottom());
            }
        }

    }
}
