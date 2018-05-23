package com.ppjun.android.smzdm.mvp.model.entity.main
import com.google.gson.annotations.SerializedName



data class ArticleList(
		@SerializedName("error_code") val errorCode: String = "",
		@SerializedName("error_msg") val errorMsg: String = "",
		@SerializedName("smzdm_id") val smzdmId: String = "",
		@SerializedName("s") val s: String = "",
		@SerializedName("data") val data: List<ArticleData> = listOf()
)


data class ArticleData(
		@SerializedName("article_channel_id") val articleChannelId: String = "",
		@SerializedName("cell_type") val cellType: String = "",
		@SerializedName("article_channel_name") val articleChannelName: String = "",
		@SerializedName("article_id") val articleId: String = "",
		@SerializedName("article_url") val articleUrl: String = "",
		@SerializedName("article_title") val articleTitle: String = "",
		@SerializedName("full_title_cn") val fullTitleCn: String = "",
		@SerializedName("article_region_title") val articleRegionTitle: String = "",
		@SerializedName("article_date") val articleDate: String = "",
		@SerializedName("article_format_date") val articleFormatDate: String = "",
		@SerializedName("article_pic") val articlePic: String = "",
		@SerializedName("probreport_id") val probreportId: String = "",
		@SerializedName("article_collection") val articleCollection: String = "",
		@SerializedName("article_comment") val articleComment: String = "",
		@SerializedName("sum_collect_comment") val sumCollectComment: String = "",
		@SerializedName("article_referrals") val articleReferrals: String = "",
		@SerializedName("article_avatar") val articleAvatar: String = "",
		@SerializedName("user_smzdm_id") val userSmzdmId: String = "",
		@SerializedName("article_filter_content") val articleFilterContent: String = "",
		@SerializedName("article_type") val articleType: String = "",
		@SerializedName("article_type_name") val articleTypeName: String = "",
		@SerializedName("article_recommend") val articleRecommend: String = "",
		@SerializedName("article_love_count") val articleLoveCount: String = "",
		@SerializedName("article_favorite") val articleFavorite: String = "",
		@SerializedName("page_timesort") val pageTimesort: String = "",
		@SerializedName("promotion_type") val promotionType: String = "",
		@SerializedName("tag_name") val tagName: String = "",
		@SerializedName("ga_category") val gaCategory: String = "",
		@SerializedName("category_name") val categoryName: String = "",
		@SerializedName("tag_category") val tagCategory: String = "",
		@SerializedName("article_mall") val articleMall: String = "",
		@SerializedName("ga_brand") val gaBrand: String = "",
		@SerializedName("time_sort") val timeSort: String = ""
)

