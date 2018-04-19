package com.ppjun.android.smzdm.mvp.model.entity.main

import com.google.gson.annotations.SerializedName


data class MainList(
        @SerializedName("rows") val rows: ArrayList<Row> = arrayListOf(),
        @SerializedName("promotion_num") val promotionNum: String = "",
        @SerializedName("promotions") val promotions: List<Any> = listOf()
)

data class Row1(
        @SerializedName("article_channel_id") val articleChannelId: String = "",
        @SerializedName("article_channel_name") val articleChannelName: String = "",
        @SerializedName("article_url") val articleUrl: String = "",
        @SerializedName("redirect_data") val redirectData: RedirectData = RedirectData(),
        @SerializedName("article_type_id") val articleTypeId: String = "",
        @SerializedName("article_type_name") val articleTypeName: String = "",
        @SerializedName("article_id") val articleId: String = "",
        @SerializedName("article_title") val articleTitle: String = "",
        @SerializedName("article_price") val articlePrice: String = "",
        @SerializedName("article_date") val articleDate: String = "",
        @SerializedName("article_format_date") val articleFormatDate: String = "",
        @SerializedName("article_unix_date") val articleUnixDate: String = "",
        @SerializedName("article_referrals") val articleReferrals: String = "",
        @SerializedName("article_is_timeout") val articleIsTimeout: String = "",
        @SerializedName("article_is_sold_out") val articleIsSoldOut: String = "",
        @SerializedName("article_mall_icon") val articleMallIcon: String = "",
        @SerializedName("article_tag") val articleTag: String = "",
        @SerializedName("article_column") val articleColumn: String = "",
        @SerializedName("article_link") val articleLink: String = "",
        @SerializedName("article_link_type") val articleLinkType: String = "",
        @SerializedName("article_link_list") val articleLinkList: List<Any> = listOf(),
        @SerializedName("article_pic") val articlePic: String = "",
        @SerializedName("article_worthy") val articleWorthy: String = "",
        @SerializedName("article_unworthy") val articleUnworthy: String = "",
        @SerializedName("article_collection") val articleCollection: String = "",
        @SerializedName("article_comment") val articleComment: String = "",
        @SerializedName("article_mall") val articleMall: String = "",
        @SerializedName("article_anonymous") val articleAnonymous: String = "",
        @SerializedName("promotion_type") val promotionType: String = "",
        @SerializedName("ga_category") val gaCategory: String = "",
        @SerializedName("article_region") val articleRegion: String = "",
        @SerializedName("article_district") val articleDistrict: String = "",
        @SerializedName("article_top") val articleTop: String = "",
        @SerializedName("time_sort") val timeSort: String = "",
        @SerializedName("matches_rules") val matchesRules: String = ""
)

data class RedirectData1(
        @SerializedName("link") val link: String = "",
        @SerializedName("link_type") val linkType: String = "",
        @SerializedName("sub_type") val subType: String = "",
        @SerializedName("link_val") val linkVal: String = "",
        @SerializedName("link_title") val linkTitle: String = "",
        @SerializedName("isv_code_second") val isvCodeSecond: String = "",
        @SerializedName("jd_isv_code") val jdIsvCode: String = ""
)


