package com.ppjun.android.smzdm.mvp.model.entity.main;

import com.google.gson.annotations.SerializedName;

public class Response<T> {

    @SerializedName("data")
    T data = null;

    public T getData() {
        return data;
    }


}
