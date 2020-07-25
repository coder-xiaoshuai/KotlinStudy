package com.example.kotlinstudy.net;

import com.example.kotlinstudy.bean.BaseResult;
import com.example.kotlinstudy.bean.Chapter;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("/wxarticle/chapters/json")
    Call<BaseResult<List<Chapter>>> requestChapters();
}
