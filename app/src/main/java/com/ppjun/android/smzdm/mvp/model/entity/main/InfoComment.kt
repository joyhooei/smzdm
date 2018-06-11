package com.ppjun.android.smzdm.mvp.model.entity.main
import com.google.gson.annotations.SerializedName



data class InfoComment(
    @SerializedName("comment_ID") val commentID: String = "",
    @SerializedName("user_smzdm_id") val userSmzdmId: String = "",
    @SerializedName("comment_author") val commentAuthor: String = "",
    @SerializedName("comment_content") val commentContent: String = "",
    @SerializedName("comment_parent") val commentParent: String = "",
    @SerializedName("comment_parent_ids") val commentParentIds: String = "",
    @SerializedName("support_count") val supportCount: String = "",
    @SerializedName("oppose_count") val opposeCount: String = "",
    @SerializedName("comment_date") val commentDate: String = "",
    @SerializedName("format_date") val formatDate: String = "",
    @SerializedName("format_date_client") val formatDateClient: String = "",
    @SerializedName("ding_class") val dingClass: String = "",
    @SerializedName("post_author") val postAuthor: String = "",
    @SerializedName("has_christmas_hat") val hasChristmasHat: Boolean = false,
    @SerializedName("floor") val floor: String = "",
    @SerializedName("head") val head: String = "",
    @SerializedName("level") val level: String = "",
    @SerializedName("level_logo") val levelLogo: String = "",
    @SerializedName("medals") val medals: List<Any> = listOf(),
    @SerializedName("official") val official: String = "",
    @SerializedName("have_current_user") val haveCurrentUser: String = "",
    @SerializedName("is_anonymous") val isAnonymous: String = "",
    @SerializedName("is_merchant") val isMerchant: String = "",
    @SerializedName("from_client") val fromClient: String = "",
    @SerializedName("from_client_version") val fromClientVersion: String = "",
    @SerializedName("from_client_uri") val fromClientUri: String = "",
    @SerializedName("from_client_suff") val fromClientSuff: String = ""
)