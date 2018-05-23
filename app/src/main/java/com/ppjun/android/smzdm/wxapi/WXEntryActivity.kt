package com.ppjun.android.smzdm.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.Constant
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.ShareBottomSheetDialogFragment.Companion.onWxShareListener
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory

class WXEntryActivity : Activity(), IWXAPIEventHandler {
    lateinit var mIWXAPI: IWXAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIWXAPI = WXAPIFactory.createWXAPI(this, Constant.WX_ID, false)
        mIWXAPI.handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        mIWXAPI.handleIntent(intent, this)
    }


    override fun onResp(resp: BaseResp?) {

        when (resp?.errCode) {

            BaseResp.ErrCode.ERR_OK -> {
                when (resp.type) {
                    ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX -> {
                        //share
                        LogUtils.debugInfo("debug=", "分享成功wx")

                        finish()
                        if (onWxShareListener != null) {
                            onWxShareListener?.onSuccess()
                        }
                    }
                    ConstantsAPI.COMMAND_SENDAUTH -> {
                        //login
                    }
                }


            }
            BaseResp.ErrCode.ERR_USER_CANCEL -> {

            }
            BaseResp.ErrCode.ERR_AUTH_DENIED -> {
            }
            else -> {
            }

        }
        finish()
    }

    override fun onReq(p0: BaseReq?) {
    }
}