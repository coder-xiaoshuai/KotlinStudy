package com.example.kotlinstudy.net

import com.example.kotlinstudy.bean.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    /**
     * 获取banner
     */
    @GET("/banner/json")
    suspend fun getBanners():Response<BaseResult<List<Banner>>>

    /**
     * 获取公众号列表
     *
     * @return
     */
    @GET("/wxarticle/chapters/json")
    fun requestChapters(): Call<BaseResult<List<PublicInfo>>>

    /**
     * 获取公众号列表
     *
     * @return
     */
    @GET("/wxarticle/chapters/json")
    suspend fun getAuthors(): Response<BaseResult<List<PublicInfo>>>

    /**
     * 获取文章列表
     *
     * @return
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    fun getArticleList(@Path("id") id: Int, @Path("page") page: Int): Call<BaseResult<ArticleWrapper>>

    /**
     * 每日一答
     *
     * @return
     */
    @GET("/wenda/list/{page}/json ")
    fun getDailyQuestionList(@Path("page") page: Int): Call<BaseResult<QuestionWrapper>>
}