package com.ppjun.android.smzdm.mvp.model.entity.main
import com.google.gson.annotations.SerializedName




data class PriceInfo(
		@SerializedName("article_id") val articleId: String = "",
		@SerializedName("is_commonbl") val isCommonbl: String = "",
		@SerializedName("article_channel_type") val articleChannelType: String = "",
		@SerializedName("article_url") val articleUrl: String = "",
		@SerializedName("wxapp_channel_type") val wxappChannelType: String = "",
		@SerializedName("is_wxapp_quan") val isWxappQuan: String = "",
		@SerializedName("article_title_prefix") val articleTitlePrefix: String = "",
		@SerializedName("article_title") val articleTitle: String = "",
		@SerializedName("article_digital_price") val articleDigitalPrice: String = "",
		@SerializedName("article_mall_region") val articleMallRegion: String = "",
		@SerializedName("article_mall_domain") val articleMallDomain: String = "",
		@SerializedName("article_mall_seo") val articleMallSeo: String = "",
		@SerializedName("article_mall_id") val articleMallId: String = "",
		@SerializedName("article_mall") val articleMall: String = "",
		@SerializedName("article_price_rmb") val articlePriceRmb: String = "",
		@SerializedName("article_price_level") val articlePriceLevel: String = "",
		@SerializedName("article_mall_region_name") val articleMallRegionName: String = "",
		@SerializedName("article_price") val articlePrice: String = "",
		@SerializedName("article_date") val articleDate: String = "",
		@SerializedName("article_format_date") val articleFormatDate: String = "",
		@SerializedName("article_mobile_exclusive") val articleMobileExclusive: String = "",
		@SerializedName("article_referrals") val articleReferrals: String = "",
		@SerializedName("article_anonymous") val articleAnonymous: Boolean = false,
		@SerializedName("article_avatar") val articleAvatar: String = "",
		@SerializedName("user_smzdm_id") val userSmzdmId: String = "",
		@SerializedName("user_id") val userId: String = "",
		@SerializedName("author_description") val authorDescription: String = "",
		@SerializedName("level_logo") val levelLogo: String = "",
		@SerializedName("author_level") val authorLevel: String = "",
		@SerializedName("fans_num") val fansNum: String = "",
		@SerializedName("dashang_list") val dashangList: List<Any> = listOf(),
		@SerializedName("article_dashang") val articleDashang: String = "",
		@SerializedName("show_dashang") val showDashang: Boolean = false,
		@SerializedName("reward_over_time") val rewardOverTime: Boolean = false,
		@SerializedName("top_category_id") val topCategoryId: String = "",
		@SerializedName("second_category_id") val secondCategoryId: String = "",
		@SerializedName("article_brand") val articleBrand: String = "",
		@SerializedName("article_brand_id") val articleBrandId: String = "",
		@SerializedName("binded_vote") val bindedVote: String = "",
		@SerializedName("tags") val tags: String = "",
		@SerializedName("is_pindan") val isPindan: String = "",
		@SerializedName("advance_booking_start_time") val advanceBookingStartTime: String = "",
		@SerializedName("advance_booking_end_time") val advanceBookingEndTime: String = "",
		@SerializedName("editor_id") val editorId: String = "",
		@SerializedName("article_link") val articleLink: String = "",
		@SerializedName("article_link_name") val articleLinkName: String = "",
		@SerializedName("article_link_type") val articleLinkType: String = "",
		@SerializedName("jd_yushou") val jdYushou: String = "",
		@SerializedName("wxapp_path") val wxappPath: String = "",
		@SerializedName("wxapp_path_type") val wxappPathType: String = "",
		@SerializedName("wxapp_link_type") val wxappLinkType: String = "",
		@SerializedName("share_wxapp_url") val shareWxappUrl: String = "",
		@SerializedName("article_pic") val articlePic: String = "",
		@SerializedName("article_small_pic") val articleSmallPic: String = "",
		@SerializedName("article_pic_width") val articlePicWidth: String = "",
		@SerializedName("article_pic_height") val articlePicHeight: String = "",
		@SerializedName("article_format_pic") val articleFormatPic: String = "",
		@SerializedName("article_collection") val articleCollection: String = "",
		@SerializedName("article_comment") val articleComment: String = "",
		@SerializedName("article_favorite") val articleFavorite: String = "",
		@SerializedName("article_worthy") val articleWorthy: String = "",
		@SerializedName("article_unworthy") val articleUnworthy: String = "",
		@SerializedName("article_filter_content") val articleFilterContent: String = "",
		@SerializedName("article_content") val articleContent: String = "",
		@SerializedName("product_intro") val productIntro: String = "",
		@SerializedName("article_content_img_list") val articleContentImgList: List<Any> = listOf(),
		@SerializedName("article_comment_open") val articleCommentOpen: String = "",
		@SerializedName("share_title") val shareTitle: String = "",
		@SerializedName("share_title_other") val shareTitleOther: String = "",
		@SerializedName("share_pic_title") val sharePicTitle: String = "",
		@SerializedName("share_pic") val sharePic: String = "",
		@SerializedName("b_share_title") val bShareTitle: String = "",
		@SerializedName("share_title_separate") val shareTitleSeparate: String = "",
		@SerializedName("share_long_pic_title") val shareLongPicTitle: String = "",
		@SerializedName("share_reward") val shareReward: String = "",
		@SerializedName("article_phrase_desc") val articlePhraseDesc: String = "",
		@SerializedName("article_show_bl_reason") val articleShowBlReason: String = "",
		@SerializedName("article_bl_reason") val articleBlReason: String = "",
		@SerializedName("article_product_focus_pic_url") val articleProductFocusPicUrl: List<ArticleProductFocusPicUrl> = listOf(),
		@SerializedName("article_activity") val articleActivity: List<Any> = listOf(),
		@SerializedName("article_edit_page_type") val articleEditPageType: String = "",
		@SerializedName("article_comment_switch") val articleCommentSwitch: String = "",
		@SerializedName("is_review") val isReview: String = "",
		@SerializedName("strategy_pub") val strategyPub: String = "",
		@SerializedName("is_jsf") val isJsf: String = "",
		@SerializedName("article_onekey_haitao_str") val articleOnekeyHaitaoStr: String = "",
		@SerializedName("is_xzjqr") val isXzjqr: String = "",
		@SerializedName("is_bcjqr") val isBcjqr: String = "",
		@SerializedName("article_lanmu") val articleLanmu: String = "",
		@SerializedName("article_link_limit") val articleLinkLimit: String = "",
		@SerializedName("dingyue_product_url") val dingyueProductUrl: String = "",
		@SerializedName("yh_type") val yhType: String = "",
		@SerializedName("stock_status") val stockStatus: String = "",
		@SerializedName("hot_comments") val hotComments: List<Any> = listOf()
)

data class ArticleProductFocusPicUrl(
		@SerializedName("pic") val pic: String = "",
		@SerializedName("width") val width: String = "",
		@SerializedName("height") val height: String = "",
		@SerializedName("is_focus") val isFocus: String = "",
		@SerializedName("pic_id") val picId: String = ""
)

















