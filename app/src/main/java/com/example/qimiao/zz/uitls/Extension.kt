package com.example.qimiao.zz.uitls

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by lk on 2018/6/8.
 */
fun Context.showToast(message: String) : Toast {
    var toast : Toast = Toast.makeText(this,message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER,0,0)
    toast.show()
    return toast
}

inline fun <reified T: Activity> Activity.startActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}
fun <T> Observable<T>.applySchedulers(): Observable<T> {
    return subscribeOn(Schedulers.io()).
            unsubscribeOn(Schedulers.io()).
            observeOn(AndroidSchedulers.mainThread())
}


//inline fun <reified T : Activity> Activity.startActivity(vararg args: Pair<String, Any>) {
//    val intent = Intent(this, T::class.java)
//    intent.putExtras(bundleOf(*args))
//    startActivity(intent)
//}

