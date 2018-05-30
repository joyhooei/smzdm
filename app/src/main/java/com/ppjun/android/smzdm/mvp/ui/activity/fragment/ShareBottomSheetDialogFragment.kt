package com.ppjun.android.smzdm.mvp.ui.activity.fragment

import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.jess.arms.http.imageloader.glide.GlideArms
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.mvp.model.entity.Share
import com.tencent.bugly.beta.ui.UILifecycleListener
import com.tencent.connect.common.Constants
import com.tencent.connect.share.QQShare
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.share_ui.view.*
import java.io.ByteArrayOutputStream
import java.io.File


class ShareBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var mIUListener: IUiListener? = null

    companion object {
        var onWxShareListener: OnWxShareListener? = null

    }

    interface OnWxShareListener {
        fun onSuccess()
        fun onFailure()
        fun onCancel()


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.share_ui, container, false)
        initView(view)
        return view
    }


    override fun onSaveInstanceState(outState: Bundle) {
        //super.onSaveInstanceState(outState)
    }

    private fun initView(view: View?) {
        val share = arguments?.getParcelable<Share>(Constant.KEY)

        view?.moreShareImg?.setOnClickListener {
            moreShare(share)
        }
        view?.moreShareText?.setOnClickListener {
            moreShare(share)
        }
        view?.copyShareImg?.setOnClickListener {
            copyShare(share)
        }
        view?.copyShareText?.setOnClickListener {
            copyShare(share)
        }
        view?.shareCancel?.setOnClickListener {
            dismiss()
        }
        view?.qqShareImg?.setOnClickListener {
            qqShare(share)
        }
        view?.qqShareText?.setOnClickListener {
            qqShare(share)
        }
        view?.wxShareImg?.setOnClickListener {
            wxShare(share)
        }
        view?.wxShareText?.setOnClickListener {
            wxShare(share)
        }

    }

    private fun wxShare(share: Share?) {

        GlideArms.with(context!!)
                .asFile()
                .load(share?.imageUrl)
                .into(object : SimpleTarget<File>() {
                    override fun onResourceReady(resource: File, transition: Transition<in File>?) {

                        val mIWXAPI = WXAPIFactory.createWXAPI(context, Constant.WX_ID)
                        val webpage = WXWebpageObject()
                        webpage.webpageUrl = share?.targetUrl
                        val msg = WXMediaMessage(webpage)
                        msg.title = share?.title
                        msg.description = share?.content
                        // val bitmap= BitmapFactory.decodeResource(context?.resources,R.mipmap.ic_launcher)
                        val bitmap = BitmapFactory.decodeFile(resource.absolutePath)
                        msg.thumbData = bmpToByteArray(bitmap, true)
                        val req = SendMessageToWX.Req()
                        req.transaction = System.currentTimeMillis().toString()
                        req.message = msg
                        req.scene = SendMessageToWX.Req.WXSceneSession


                        onWxShareListener = object : OnWxShareListener {
                            override fun onSuccess() {
                                Toast.makeText(context, getString(R.string.share_success), Toast.LENGTH_SHORT).show()
                                dismissAllowingStateLoss()
                            }

                            override fun onFailure() {
                                LogUtils.debugInfo("debug=", "分享failure fragment")
                            }

                            override fun onCancel() {
                                LogUtils.debugInfo("debug=", "分享cancel fragment")
                            }
                        }

                        mIWXAPI.sendReq(req)

                    }


                })

    }


    private fun qqShare(share: Share?) {
        Log.i("debug=", share?.imageUrl)
        val mTencent = Tencent.createInstance(Constant.QQ_ID, context)
        val bundle = Bundle()
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, share?.targetUrl)
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, share?.imageUrl)
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, share?.title)
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, share?.content)
        bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE)
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT)
        mIUListener = object : IUiListener {
            override fun onComplete(p0: Any?) {
                dismiss()
                ArmsUtils.snackbarText(getString(R.string.share_success))
            }

            override fun onCancel() {
                dismiss()
            }

            override fun onError(p0: UiError?) {
                dismiss()
            }

        }
        mTencent.shareToQQ(context as Activity?, bundle, mIUListener)

    }

    private fun moreShare(share: Share?) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, share?.title + " " + share?.targetUrl)
        intent.type = "text/plain"
        startActivity(intent)
        dismiss()
    }

    private fun copyShare(share: Share?) {
        val clipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.primaryClip = ClipData.newPlainText(Constant.LABEL, share?.targetUrl)
        ArmsUtils.snackbarText(getString(R.string.clip_tip))
        dismiss()
    }

    fun onQQActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            mIUListener?.let {
                Tencent.onActivityResultData(requestCode, resultCode, data, it)
            }

        }

    }

    fun bmpToByteArray(bmp: Bitmap, needRecycle: Boolean): ByteArray {
        val output = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output)
        if (needRecycle) {
            bmp.recycle()
        }

        val result = output.toByteArray()
        try {
            output.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        super.show(manager, tag)
//           try {
//            val c=Class.forName("android.support.v4.app.DialogFragment")
//            val con = c.getConstructor()
//            val obj = con.newInstance()
//            val dismissed = c.getDeclaredField("mDismissed")
//            dismissed.isAccessible=true
//            dismissed.set(obj,false)
//            val shownByMe = c.getDeclaredField("mShownByMe")
//            shownByMe.isAccessible=true
//            shownByMe.set(obj,true)
//        } catch ( e:Exception) {
//            e.printStackTrace()
//        }
//            val ft = manager?.beginTransaction()
//            ft?.add(this, tag)
//            ft?.commitAllowingStateLoss()
    }

}

