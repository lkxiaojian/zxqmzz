package com.example.qimiao.zz.uitls;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.qimiao.zz.R;
import com.example.qimiao.zz.uitls.ui.UIUtils;

import java.lang.reflect.Field;

public class ToolbarUtils {
    public static TextView getToolbarTitleView(Toolbar toolbar) {

        try {
            Field field = toolbar.getClass().getDeclaredField("mTitleTextView");
            field.setAccessible(true);

            Object object = field.get(toolbar);
            if (object != null) {
                TextView mTitleTextView = (TextView) object;
                return mTitleTextView;
            }
        } catch (IllegalAccessException e) {
        } catch (NoSuchFieldException e) {
        } catch (Exception e) {
        }
        return null;
    }

    public static void setMarqueeForToolbarTitleView(final Toolbar toolbar) {
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                TextView mTitleTextView = getToolbarTitleView(toolbar);
                if (mTitleTextView == null) {
                    return;
                }
                mTitleTextView.setHorizontallyScrolling(true);
                mTitleTextView.setMarqueeRepeatLimit(-1);
                mTitleTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                mTitleTextView.setSelected(true);
            }
        });
    }


    /**
     * @param toolbar       toolBar 对象
     * @param activity      上下文
     * @param centterTitle  中间的标题
     * @param rightTile     左边标题
     * @param rightSubTitle 左边子标题
     * @param type          是否显示菜单栏  0 不显示 1 显示
     */

    public static void setToolTitle(final Toolbar toolbar, Activity activity, String centterTitle, String rightTile, String rightSubTitle, int type) {
        UIUtils.getInstance(activity);
        if (rightTile != null && !"".equals(rightTile)) {
            toolbar.setTitle(rightTile);
            toolbar.setSubtitle(rightSubTitle);
        }

        if (type == 1) {
            toolbar.setOverflowIcon(ContextCompat.getDrawable(activity, R.mipmap.more));
        }
        if (centterTitle != null && !"".equals(centterTitle)) {
            TextView textView = activity.findViewById(R.id.tv_center_title);
            textView.setText(centterTitle);
        }
        ToolbarUtils.setMarqueeForToolbarTitleView(toolbar);
        //设置沉浸式，并且toolbar设置padding
        StatusBarUtil.setStateBar(activity, toolbar);
    }
}
