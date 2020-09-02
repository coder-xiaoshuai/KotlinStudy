package com.example.kotlinstudy.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.ToastUtils
import com.example.kotlinstudy.bean.*
import com.example.kotlinstudy.net.KotlinStudyApi
import com.example.kotlinstudy.net.NetExceptionHandle
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WanAndroidViewModel : ViewModel() {
    val authorLiveData: MutableLiveData<List<PublicInfo>> by lazy {
        MutableLiveData<List<PublicInfo>>()
    }

    val questionLiveData: MutableLiveData<QuestionWrapper> by lazy {
        MutableLiveData<QuestionWrapper>()
    }

    val bannerLiveData: MutableLiveData<List<Banner>> by lazy {
        MutableLiveData<List<Banner>>()
    }

    val handler = CoroutineExceptionHandler { _, exception ->
    }


    /**
     * 使用协程配合retrofit完成网络请求
     *
     * 包含了请求banner数据和请求推荐公众号列表数据
     */
    fun getRecommendData() {
        viewModelScope.launch(NetExceptionHandle.toastExceptionHandler) {
            val bannerResponse = withContext(Dispatchers.IO) {
                KotlinStudyApi.api?.getBanners()
            }

            Log.i("xiaoshuai", "请求banner成功")
            if (bannerResponse?.isSuccessful == true) {
                val body = bannerResponse.body()
                bannerResponse?.body()?.let {
                    if (!it.data.isNullOrEmpty()) {
                        bannerLiveData.value = it.data
                    }
                }
            } else {
                ToastUtils.show("获取banner失败")
            }

            val response = withContext(Dispatchers.IO) {
                KotlinStudyApi.api?.getAuthors()
            }

            Log.i("xiaoshuai", "请求列表成功")
            if (response?.isSuccessful == true) {
                response.body()?.let {
                    if (!it.data.isNullOrEmpty()) {
                        authorLiveData.value = it.data
                    }
                }
            } else {
                ToastUtils.show("获取公众号列表失败")
            }
        }

    }

    fun getDailyQuestionList(page: Int) {
        //网络请求  如果是请求玩安卓的可以直接使用api去使用 baseUrl已经写死  如果想自定义baseUrl可以直接使用KotlinStudyApi.singleCustomRequest
        KotlinStudyApi.api?.getDailyQuestionList(page)?.enqueue(object :
            Callback<BaseResult<QuestionWrapper>> {
            override fun onFailure(call: Call<BaseResult<QuestionWrapper>>, t: Throwable) {
                ToastUtils.show("请求失败${t}")
                questionLiveData.value = null
            }

            override fun onResponse(call: Call<BaseResult<QuestionWrapper>>, response: Response<BaseResult<QuestionWrapper>>) {
                if (response.isSuccessful) {
                    questionLiveData.value = response.body()?.data
                } else {
                    questionLiveData.value = null
                    ToastUtils.show("服务器维护中,请求失败")
                }
            }
        })
    }

}