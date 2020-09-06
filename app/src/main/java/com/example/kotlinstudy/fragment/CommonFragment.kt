package com.example.kotlinstudy.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.utils.ToastUtils
import com.example.common_ui.base.BaseFragment
import com.example.kotlinstudy.R
import com.example.kotlinstudy.adapter.CommonListAdapter
import kotlinx.android.synthetic.main.fragment_common.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CommonFragment : BaseFragment() {

    val mainScope = MainScope()
    val dataList = arrayListOf<String>("列表内容",
        "列表内容",
        "列表内容",
        "列表内容",
        "列表内容",
        "列表内容",
        "列表内容",
        "列表内容",
        "列表内容",
        "列表内容",
        "列表内容",
        "列表内容")

    override fun getLayoutResId(): Int {
        return R.layout.fragment_common
    }

    override fun onInflated(contentView: View?, savedInstanceState: Bundle?) {
        //禁掉下拉刷新
        refresh_layout.setEnableRefresh(false)

        activity?.let {
            rv_common_list.layoutManager =
                LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
            rv_common_list.adapter = CommonListAdapter(it, dataList)
        }


        refresh_layout.setOnLoadMoreListener {
            mainScope.launch {
                delay(1000)
                refresh_layout.finishLoadMore()
                ToastUtils.show("加载完成")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}