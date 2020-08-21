package com.example.kotlinstudy.net;

import com.example.kotlinstudy.bean.Article;
import com.example.kotlinstudy.bean.ArticleWrapper;
import com.example.kotlinstudy.bean.Banner;
import com.example.kotlinstudy.bean.BaseResult;
import com.example.kotlinstudy.bean.PublicInfo;
import com.example.kotlinstudy.bean.QuestionWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    /**
     * 获取banner
     *
     * @return
     */
    @GET("/banner/json")
    Call<BaseResult<List<Banner>>> getBanners();

    /**
     * 获取公众号列表
     *
     * @return
     */
    @GET("/wxarticle/chapters/json")
    Call<BaseResult<List<PublicInfo>>> requestChapters();

    /**
     * 获取文章列表
     *
     * @return
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    Call<BaseResult<ArticleWrapper>> getArticleList(@Path("id") int id, @Path("page") int page);

    /**
     * 每日一答
     *
     * @return
     */
    @GET("/wenda/list/{page}/json ")
    Call<BaseResult<QuestionWrapper>> getDailyQuestionList(@Path("page") int page);
}
