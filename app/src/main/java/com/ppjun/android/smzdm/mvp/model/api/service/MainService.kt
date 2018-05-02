package com.ppjun.android.smzdm.mvp.model.api.service

import com.ppjun.android.smzdm.mvp.model.entity.main.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {

    /**
     * 首页
     */
    @GET("/v1/home/articles?f=wxapp&wxapp=zdmapp")
    fun getMain(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<MainList>

    /**
     * 好价列表
     */
    @GET("/v1/home/articles_new?f=wxapp&wxapp=zdmapp")
    fun priceList(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<PriceList>

    /**
     * 好文列表
     */
    @GET("/v1/wxapp/zdmapp/good_articles_list?f=wxapp&wxapp=zdmapp")
    fun articleList(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<ArticleList>

    /**
     * 好价详情
     */
    @GET("/v1/wxapp/zdmapp/youhui_detail?f=wxapp&wxapp=zdmapp")
    fun priceInfo(@Query("id") id: String): Observable<Response<PriceInfo>>

    /**
     * 好文详情
     */
    @GET("/v1/wxapp/zdmapp/post_detail?f=wxapp&wxapp=zdmapp")
    fun articleInfo(@Query("id") id: String): Observable<Response<ArticleInfo>>

    /**
     * 资讯详情
     */
    @GET("/v1/wxapp/zdmapp/news_detail?f=wxapp&wxapp=zdmapp")
    fun newsInfo(@Query("id") id: String): Observable<Response<NewsInfo>>

    /**
     * 搜索好价
     */
    @GET("/v1/list?f=wxapp&wxapp=zdmapp&order=score&type=good_price")
    fun priceSearch(@Query("keyword") keyword: String,@Query("offset") offset: Int, @Query("limit") limit: Int):
            Observable<PriceList>

    /**
     * 搜索好文
     */
    @GET("/v1/list?f=wxapp&wxapp=zdmapp&type=yuanchuang")
    fun articleSearch(@Query("keyword") keyword: String,@Query("offset") offset: Int, @Query("limit") limit: Int):
            Observable<Response<ArticleSearch<ArticleData>>>
}