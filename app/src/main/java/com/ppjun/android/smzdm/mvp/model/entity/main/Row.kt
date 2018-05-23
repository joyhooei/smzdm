package com.ppjun.android.smzdm.mvp.model.entity.main

import com.google.gson.annotations.SerializedName

data class RowData<T>(@SerializedName("rows")var rows:ArrayList<T>)
