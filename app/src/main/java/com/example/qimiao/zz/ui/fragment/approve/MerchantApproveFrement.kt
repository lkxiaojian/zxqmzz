package com.example.qimiao.zz.ui.fragment.approve

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.qimiao.zz.App.MyApplication
import com.example.qimiao.zz.R
import com.example.qimiao.zz.mvp.m.bean.ImageResult
import com.example.qimiao.zz.ui.fragment.base.BaseFragment
import com.example.qimiao.zz.ui.popupwindow.OptionBottomDialog
import com.example.qimiao.zz.uitls.Constant
import com.example.qimiao.zz.uitls.UriParseUtils
import com.example.urilslibrary.Utils
import com.google.gson.Gson
import com.netease.image.library.CompressImageManager
import com.netease.image.library.bean.Photo
import com.netease.image.library.config.CompressConfig
import com.netease.image.library.listener.CompressImage
import com.netease.image.library.utils.CachePathUtils
import com.netease.image.library.utils.CommonUtils
import com.netease.image.library.utils.Constants
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.merchant_approve_frement.*
import okhttp3.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File
import java.io.IOException
import java.util.ArrayList

class MerchantApproveFrement : BaseFragment(), CompressImage.CompressListener {


    var mView: View? = null
    private var compressConfig: CompressConfig? = null // 压缩配置
    private var cameraCachePath: String? = null // 拍照源文件路径
    private var dialog: ProgressDialog? = null // 压缩加载框
    private var rxPermissions: RxPermissions? = null
    private var isFalg = true
    private var imagesMap: HashMap<String, File>? = hashMapOf()
    private val client: OkHttpClient = OkHttpClient()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mView = inflater.inflate(R.layout.merchant_approve_frement, container, false)
        return mView
    }

    override fun getLayoutView(): View {
        return mView!!
    }

    override fun initView(): Fragment {
        if (isFalg) {
            EventBus.getDefault().register(this)
            rxPermissions = RxPermissions(this)
            setCoifgIamge()
            setOnclick()
            isFalg = false
        }
        return this
    }

    private fun setCoifgIamge() {
        compressConfig = CompressConfig.builder()
                .setUnCompressMinPixel(1000) // 最小像素不压缩，默认值：1000
                .setUnCompressNormalPixel(2000) // 标准像素不压缩，默认值：2000
                .setMaxPixel(1000) // 长或宽不超过的最大像素 (单位px)，默认值：1200
                .setMaxSize(100 * 1024) // 压缩到的最大大小 (单位B)，默认值：200 * 1024 = 200KB
                .enablePixelCompress(true) // 是否启用像素压缩，默认值：true
                .enableQualityCompress(true) // 是否启用质量压缩，默认值：true
                .enableReserveRaw(true) // 是否保留源文件，默认值：true
                .setCacheDir("") // 压缩后缓存图片路径，默认值：Constants.COMPRESS_CACHE
                .setShowCompressDialog(true) // 是否显示压缩进度条，默认值：false
                .create()
    }

    private fun setOnclick() {
        im_id_card_z.setOnClickListener {
            openDialog(it, "0")
        }
        im_id_card_f.setOnClickListener {
            openDialog(it, "1")
        }
        im_id_card_y.setOnClickListener {
            openDialog(it, "2")
        }

        bt_commit.setOnClickListener {
            var shopName = et_shop_name.text.toString()
            var shopAddress = et_shop_address.text.toString()
            var shopPersion = et_shop_person.text.toString()
            var shopPhone = et_shop_phone.text.toString()

            if (TextUtils.isEmpty(shopName)) {
                Utils.ToastShort(MyApplication.mAppContext, "商家名称为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(shopAddress)) {
                Utils.ToastShort(MyApplication.mAppContext, "商家地址为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(shopPersion)) {
                Utils.ToastShort(MyApplication.mAppContext, "联系人姓名为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(shopPhone)) {
                Utils.ToastShort(MyApplication.mAppContext, "联系电话为空")
                return@setOnClickListener
            }

            if (imagesMap?.size != 3) {
                Utils.ToastShort(MyApplication.mAppContext, "证件上传不全")
                return@setOnClickListener
            }


        }
    }

    override fun <T> setData(type: String, bean: T) {

    }

    override fun onError(type: String, error: Throwable) {

    }


    fun openDialog(view: View, type: String) {

        //权限使用
        rxPermissions
                ?.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                ?.subscribe { granted ->
                    if (granted) {
                        val messageList = ArrayList<String>()
                        messageList.add("拍照")
                        messageList.add("从相册选择")
                        val optionBottomDialog = OptionBottomDialog(this.context, messageList)
                        optionBottomDialog.setItemClickListener { parent, view, position, id ->
                            if (position == 0) {
                                if ("0" == type) {
                                    cameraImage(view, Constant.CAMERA_CODE_ID_Z)
                                } else if ("1" == type) {
                                    cameraImage(view, Constant.CAMERA_CODE_ID_F)
                                } else if ("2" == type) {
                                    cameraImage(view, Constant.CAMERA_CODE_ID_ZY)
                                }

                            } else if (position == 1) {

                                if ("0" == type) {
                                    albumImage(view, Constant.ALBUN_CODE_ID_Z)
                                } else if ("1" == type) {
                                    albumImage(view, Constant.ALBUN_CODE_ID_F)
                                } else if ("2" == type) {
                                    albumImage(view, Constant.ALBUN_CODE_ID_FY)
                                }
                            }

                            optionBottomDialog.dismiss()
                        }
                    } else {
                        Utils.ToastShort(MyApplication.getAppContext(), "权限已经拒绝,打开权限重新使用！！！")

                    }
                }

    }


    // 点击拍照
    fun cameraImage(view: View, CODE: Int) {
        // FileProvider
        val outputUri: Uri
        val file = CachePathUtils.getCameraCacheFile()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            outputUri = UriParseUtils.getCameraOutPutUri(this.context, file)
        } else {
            outputUri = Uri.fromFile(file)
        }
        cameraCachePath = file.absolutePath
        // 启动拍照
        CommonUtils.hasCamera(this.activity, CommonUtils.getCameraIntent(outputUri), CODE)
    }

    // 点击相册
    fun albumImage(view: View, CODE: Int) {
        CommonUtils.openAlbum(this.activity, CODE)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ImageResult) {
//        Log.e("tag", event.data.dataString)

        if (event.resultCode == Activity.RESULT_OK) {
            if (event.requestCode == Constant.CAMERA_CODE_ID_ZY || event.requestCode == Constant.CAMERA_CODE_ID_Z || event.requestCode == Constant.CAMERA_CODE_ID_F) {
                preCompress(cameraCachePath!!, event.requestCode)
            } else if (event.requestCode == Constant.ALBUN_CODE_ID_FY || event.requestCode == Constant.ALBUN_CODE_ID_Z || event.requestCode == Constant.ALBUN_CODE_ID_F) {
                if (event.data != null) {
                    val uri = event.data.data
                    val path = UriParseUtils.getPath(this.context, uri)
                    // 压缩（集合？单张）
                    preCompress(path, event.requestCode)
                }

            }
        } else {
            Utils.ToastShort(MyApplication.mAppContext, "图片读取失败")
        }


    }

    override fun onCompressSuccess(arrayList: ArrayList<Photo>) {
        Log.e("netease >>> ", "压缩成功")
        if (dialog != null && !this.activity?.isFinishing!!) {
            dialog?.dismiss()
        }
        if (arrayList != null && arrayList.size > 0) {
            var phone = arrayList[0]
            glideImage(phone)
        }


    }

    override fun onCompressFailed(arrayList: ArrayList<Photo>, error: String) {
        Log.e("netease >>> ", error)
        Utils.ToastShort(MyApplication.mAppContext, "图片读取失败")
        if (dialog != null && !this.activity?.isFinishing!!) {
            dialog?.dismiss()
        }
    }

    // 准备压缩，封装图片集合
    private fun preCompress(photoPath: String, type: Int) {
        val photos = ArrayList<Photo>()
        photos.add(Photo(photoPath, type))
        if (photos.isNotEmpty()) compress(photos)
    }

    // 开始压缩
    private fun compress(photos: ArrayList<Photo>) {
        if (compressConfig!!.isShowCompressDialog) {
            Log.e("netease >>> ", "开启了加载框")
            dialog = CommonUtils.showProgressDialog(this.activity, "压缩中……")
        }
        CompressImageManager.build(this.context, compressConfig, photos, this).compress()
    }

    private fun glideImage(phone: Photo) {
        var url = phone.compressPath
        if (url == null || url.isEmpty()) {
            url = phone.originalPath
        }
        var imageView: ImageView? = null
        var errorImage = R.mipmap.id_card_z
        if (phone.type == Constant.CAMERA_CODE_ID_Z || phone.type == Constant.ALBUN_CODE_ID_Z) {
            imagesMap?.put("id_card_z", File(url))
            errorImage = R.mipmap.id_card_z
            imageView = im_id_card_z
        } else if (phone.type == Constant.ALBUN_CODE_ID_F || phone.type == Constant.CAMERA_CODE_ID_F) {
            imagesMap?.put("id_card_f", File(url))
            errorImage = R.mipmap.id_card_f
            imageView = im_id_card_f
        } else if (phone.type == Constant.ALBUN_CODE_ID_FY || phone.type == Constant.CAMERA_CODE_ID_ZY) {
            imagesMap?.put("id_card_zy", File(url))
            errorImage = R.mipmap.id_card_y
            imageView = im_id_card_y
        }
        Glide.with(context).load(url)
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(errorImage)
                .error(errorImage)
                .into(imageView)
    }


    private fun uploadFiles(fileList: List<File>, which: Int) { // which 1 image,2 video

        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        for (file in fileList) {
            if (file != null) {
                builder.addFormDataPart("files", file.name, RequestBody.create(if (which == 1) Constant.MEDIA_TYPE_JPG else Constant.MEDIA_TYPE_MP4, file))
            }
        }
        val requestBody = builder.build()
        val request = Request.Builder()
                .url("url")
                .post(requestBody)
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("上传失败:e.getLocalizedMessage() = " + e.localizedMessage)
//                dismissLoadingDialog()
//                U.showToast("文件上传错误，请重新上传")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val JsonStr = response.body()!!.string()
                println("上传照片成功：response = $JsonStr")
//                val entity = Gson().fromJson<Any>(JsonStr, ReleaseEntity::class.java)

            }
        })
    }

}