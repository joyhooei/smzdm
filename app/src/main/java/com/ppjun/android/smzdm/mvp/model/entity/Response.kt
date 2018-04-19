package com.ppjun.android.smzdm.mvp.model.entity
import com.google.gson.annotations.SerializedName



data class Response<out T>(
		@SerializedName("error_code") val errorCode: String = "",
		@SerializedName("error_msg") val errorMsg: String = "",
		@SerializedName("smzdm_id") val smzdmId: String = "",
		@SerializedName("s") val s: String = "",
		@SerializedName("data") val data: T
)

