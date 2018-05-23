package com.ppjun.android.smzdm.mvp.model.entity.main
import com.google.gson.annotations.SerializedName



data class MainBanner(
    @SerializedName("id") val id: String = "",
    @SerializedName("img") val img: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("promotion_type") val promotionType: String = "",
    @SerializedName("logo") val logo: String = "",
    @SerializedName("logo_title") val logoTitle: String = "",
    @SerializedName("link") val link: String = "",
    @SerializedName("redirect_data") val redirectData: RedirectData = RedirectData(),
    @SerializedName("channel") val channel: String = "",
    @SerializedName("wxapp_path") val wxappPath: String = "",
    @SerializedName("wxapp_path_type") val wxappPathType: String = ""
)

data class RedirectData(
    @SerializedName("link") val link: String = "",
    @SerializedName("link_type") val linkType: String = "",
    @SerializedName("sub_type") val subType: String = "",
    @SerializedName("link_val") val linkVal: String = "",
    @SerializedName("link_title") val linkTitle: String = "",
    @SerializedName("isv_code_second") val isvCodeSecond: String = "",
    @SerializedName("jd_isv_code") val jdIsvCode: String = "",
    @SerializedName("share_img") val shareImg: String = ""
)