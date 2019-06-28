package com.example.qimiao.zz.mvp.p;

import android.content.Context;

import com.example.qimiao.zz.mvp.m.timer.RxTimerMoulde;
import com.example.qimiao.zz.mvp.p.imbl.AbsRxTimerPresenter;
import com.example.qimiao.zz.mvp.v.TimerView;


/**
 * Created by lk on 2017/12/31.
 */

public class RxTimerPresenter extends AbsRxTimerPresenter {
    private RxTimerMoulde moulde;
    private TimerView view;

    public RxTimerPresenter(Context context) {
        this.moulde = new RxTimerMoulde();
        this.view= (TimerView) context;
    }

    public void timer(int time){
        this.moulde.Rxtimer(time,view);
    }
}
