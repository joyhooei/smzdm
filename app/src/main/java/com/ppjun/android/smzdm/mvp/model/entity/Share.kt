package com.ppjun.android.smzdm.mvp.model.entity

import android.os.Parcel
import android.os.Parcelable

data class Share(val title:String="",val content:String="",val targetUrl: String="",val imageUrl:String="" ):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(targetUrl)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Share> {
        override fun createFromParcel(parcel: Parcel): Share {
            return Share(parcel)
        }

        override fun newArray(size: Int): Array<Share?> {
            return arrayOfNulls(size)
        }
    }
}