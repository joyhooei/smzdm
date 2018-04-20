package com.ppjun.android.smzdm.mvp.model.api.service

import com.ppjun.android.smzdm.mvp.model.entity.Response
import com.ppjun.android.smzdm.mvp.model.entity.main.MainList
import com.ppjun.android.smzdm.mvp.model.entity.main.Row
import com.ppjun.android.smzdm.mvp.model.entity.main.Rows
import com.ppjun.android.smzdm.mvp.model.entity.main.TestBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {
//@Query("f") wxapp:String, @Query("wxapp")zdmapp:String,
    @GET("/v1/home/articles?f=wxapp&wxapp=zdmapp")

    fun getMain( @Query("offset") offset: Int, @Query("limit") limit: Int): Observable<TestBean>
}