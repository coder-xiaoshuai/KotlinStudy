package com.example.kotlinstudy.net

import com.example.common.utils.GlobalConfig
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * 网络请求工具类
 *
 * @author zhangshuai
 */
object KotlinStudyApi {
    private var httpClient: OkHttpClient? = null
    var api: Api? = null
        get() {
            if (field == null) {
                field = createApi()
            }
            return field
        }
        private set
    private const val baseUrl = "https://wanandroid.com"
    private fun createOkHttpClient(): OkHttpClient {
        val cacheFile = File(GlobalConfig.getContext().cacheDir, "cache")
        val cache = Cache(cacheFile, 1024 * 1024 * 100) //100Mb
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }

    private fun createApi(): Api {
        val gson =
            GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create()
        if (httpClient == null) {
            httpClient = createOkHttpClient()
        }
        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl).build().create(Api::class.java)
    }

    /**
     * 用于单次自定义请求
     *
     * @param baseUrl
     * @return
     */
    fun singleCustomRequest(baseUrl: String?): Api {
        val client = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl).build()
        return retrofit.create(Api::class.java)
    }
}