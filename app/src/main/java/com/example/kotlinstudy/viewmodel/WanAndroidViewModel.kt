package com.example.kotlinstudy.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.common.utils.ToastUtils
import com.example.kotlinstudy.bean.BaseResult
import com.example.kotlinstudy.bean.PublicInfo
import com.example.kotlinstudy.net.KotlinStudyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WanAndroidViewModel : ViewModel() {
    val authorLiveData: MutableLiveData<List<PublicInfo>> by lazy {
        MutableLiveData<List<PublicInfo>>()
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

}