package com.ppjun.android.smzdm.mvp.model.entity.main
import com.google.gson.annotations.SerializedName



data class PriceList(
		@SerializedName("error_code") val errorCode: String = "",
		@SerializedName("error_msg") val errorMsg: String = "",
		@SerializedName("smzdm_id") val smzdmId: String = "",
		@SerializedName("s") val s: String = "",
		@SerializedName("data") val data: Data = Data()
)

data class Data(
		@SerializedName("rows") val rows: List<PriceRow> = listOf(),
		@SerializedName("total") val total: String = "",
		@SerializedName("promotion_num") val promotionNum: String = "",
		@SerializedName("promotions") val promotions: List<Any> = listOf()
)

data class PriceRow(
		@SerializedName("article_type_id") val articleTypeId: String = "",
		@SerializedName("article_channel_id") val articleChannelId: String = "",
		@SerializedName("article_channel_name") val articleChannelName: String = "",
		@SerializedName("article_type_name") val articleTypeName: String = "",
		@SerializedName("article_url") val articleUrl: String = "",
		@SerializedName("article_id") val articleId: String = "",
		@SerializedName("article_title") val articleTitle: String = "",
		@SerializedName("article_price") val articlePrice: String = "",
		@SerializedName("article_date") val articleDate: String = "",
		@SerializedName("article_format_date") val articleFormatDate: String = "",
		@SerializedName("article_unix_date") val articleUnixDate: String = "",
		@SerializedName("article_referrals") val articleReferrals: String = "",
		@SerializedName("article_top") val articleTop: String = "",
		@SerializedName("article_is_timeout") val articleIsTimeout: String = "",
		@SerializedName("article_is_sold_out") val articleIsSoldOut: String = "",
		@SerializedName("article_mall_icon") val articleMallIcon: String = "",
		@SerializedName("article_tag") val articleTag: String = "",
		@SerializedName("article_column") val articleColumn: String = "",
		@SerializedName("article_tag_list") val articleTagList: List<String> = listOf(),
		@SerializedName("is_jdz") val isJdz: String = "",
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
		@SerializedName("article_region") val articleRegion: String = "",
		@SerializedName("article_wxapp_region") val articleWxappRegion: String = "",
		@SerializedName("article_first_channel_name") val articleFirstChannelName: String = "",
		@SerializedName("article_second_channel_name") val articleSecondChannelName: String = "",
		@SerializedName("article_district") val articleDistrict: String = "",
		@SerializedName("cell_type") val cellType: String = "",
		@SerializedName("ga_category") val gaCategory: String = "",
		@SerializedName("ga_brand") val gaBrand: String = ""
)

