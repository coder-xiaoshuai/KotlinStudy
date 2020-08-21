package com.example.kotlinstudy.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.ToastUtils
import com.example.kotlinstudy.bean.*
import com.example.kotlinstudy.net.KotlinStudyApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
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

    fun getAuthorList() {
        //网络请求  如果是请求玩安卓的可以直接使用api去使用 baseUrl已经写死  如果想自定义baseUrl可以直接使用KotlinStudyApi.singleCustomRequest
        KotlinStudyApi.api?.requestChapters()?.enqueue(object :
            Callback<BaseResult<List<PublicInfo>>> {
            override fun onFailure(call: Call<BaseResult<List<PublicInfo>>>, t: Throwable) {
                ToastUtils.show("请求失败${t}")
                Log.i("zs", t.toString())
            }

            override fun onResponse(call: Call<BaseResult<List<PublicInfo>>>, response: Response<BaseResult<List<PublicInfo>>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (!it.data.isNullOrEmpty()) {
                            authorLiveData.value = it.data
                        }
                    }
                } else {
                    ToastUtils.show("服务器维护中,请求失败")
                }
            }
        })
    }

    fun getBanners() {
        KotlinStudyApi.api?.banners?.enqueue(object :
            Callback<BaseResult<List<Banner>>> {
            override fun onFailure(call: Call<BaseResult<List<Banner>>>, t: Throwable) {
                ToastUtils.show("请求失败${t}")
            }

            override fun onResponse(call: Call<BaseResult<List<Banner>>>, response: Response<BaseResult<List<Banner>>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    response.body()?.let {
                        if (!it.data.isNullOrEmpty()) {
                            bannerLiveData.value = it.data
                        }
                    }
                } else {
                    ToastUtils.show("服务器维护中,请求失败")
                }
            }
        })
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