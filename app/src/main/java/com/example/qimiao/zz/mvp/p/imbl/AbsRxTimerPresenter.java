package com.example.qimiao.zz.mvp.p.imbl;

import com.example.qimiao.zz.mvp.v.TimerView;
/**
 * Created by lk on 2017/12/31.
 */

public abstract class AbsRxTimerPresenter<V extends TimerView> {
    private  V view;
    public V getView() {
        return view;
    }
}
