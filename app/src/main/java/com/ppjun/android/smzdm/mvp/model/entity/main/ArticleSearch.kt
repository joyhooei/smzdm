package com.ppjun.android.smzdm.mvp.model.entity.main
import com.google.gson.annotations.SerializedName



data class ArticleSearch<T>(
        @SerializedName("rows") val rows: ArrayList<T>
)


