package com.ppjun.android.smzdm.mvp.model.api.service

import com.ppjun.android.smzdm.mvp.model.entity.main.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {

    @GET("/v1/home/articles?f=wxapp&wxapp=zdmapp")
    fun getMain(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<MainList>

    @GET("/v1/home/articles_new?f=wxapp&wxapp=zdmapp")
    fun priceList(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<PriceList>

    @GET("/v1/wxapp/zdmapp/good_articles_list?f=wxapp&wxapp=zdmapp")
    fun articleList(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<ArticleList>

    @GET("/v1/wxapp/zdmapp/youhui_detail?f=wxapp&wxapp=zdmapp")
    fun priceInfo(@Query("id") id: String): Observable<Response<PriceInfo>>

    @GET("/v1/wxapp/zdmapp/post_detail?f=wxapp&wxapp=zdmapp")
    fun articleInfo(@Query("id") id: String): Observable<Response<ArticleInfo>>

    @GET("/v1/wxapp/zdmapp/news_detail?f=wxapp&wxapp=zdmapp")
    fun newsInfo(@Query("id") id: String): Observable<Response<NewsInfo>>

}