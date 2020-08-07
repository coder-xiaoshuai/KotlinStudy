package com.example.kotlinstudy.activity

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.utils.ToastUtils
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.example.kotlinstudy.adapter.AuthorListAdapter
import com.example.kotlinstudy.bean.BaseResult
import com.example.kotlinstudy.bean.PublicInfo
import com.example.kotlinstudy.net.KotlinStudyApi
import kotlinx.android.synthetic.main.activity_author_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAuthorList()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_author_list
    }

    /**
     * 获取公众号作者列表
     */
    private fun getAuthorList() {
        //网络请求  如果是请求玩安卓的可以直接使用api去使用 baseUrl已经写死  如果想自定义baseUrl可以直接使用KotlinStudyApi.singleCustomRequest
        KotlinStudyApi.api?.requestChapters()?.enqueue(object :
            Callback<BaseResult<List<PublicInfo>>> {
            override fun onFailure(call: Call<BaseResult<List<PublicInfo>>>, t: Throwable) {
                ToastUtils.show("请求失败${t}")
                Log.i("zs", t.toString())
            }

            override fun onResponse(call: Call<BaseResult<List<PublicInfo>>>, response: Response<BaseResult<List<PublicInfo>>>) {
                if (response.isSuccessful) {
                    rv_author_list.layoutManager = LinearLayoutManager(this@AuthorListActivity, LinearLayoutManager.VERTICAL, false)
                    rv_author_list.adapter = AuthorListAdapter(this@AuthorListActivity, response.body()?.data as ArrayList<PublicInfo>?)
                }else{
                    ToastUtils.show("服务器维护中,请求失败")
                }
            }
        })
    }

}