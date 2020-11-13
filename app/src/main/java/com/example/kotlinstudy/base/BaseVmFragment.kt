package com.example.kotlinstudy.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinstudy.ext.getVmClazz
import com.example.kotlinstudy.manager.NetworkStateManager
import com.example.kotlinstudy.net.NetState

/**
 * 描述　: ViewModelFragment基类，自动把ViewModel注入Fragment
 */
abstract class BaseVmFragment<VM : BaseViewModel> : Fragment() {
    private var isFirst: Boolean = true
    lateinit var mViewModel: VM
    lateinit var mActivity: AppCompatActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AppCompatActivity) {
            mActivity = context as AppCompatActivity
        } else {
            throw Exception("please use AppCompatActivity instead of using activity")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst = true
        mViewModel = createViewModel()
        initView(savedInstanceState)
        createObserver()
        onVisible()
        registerDefUIChange()
        initData()
    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            //延迟加载0.12秒加载 避免fragment跳转动画和渲染ui同时进行，出现些微的小卡顿
            view?.postDelayed({
                lazyLoadData()
                //在fragment中，只有懒加载过了才能开启网络变化监听
                NetworkStateManager.instance.mNetworkStateCallback.observe(viewLifecycleOwner,
                    Observer {
                        //不是首次订阅时调用方法，防止数据第一次监听错误
                        if (!isFirst) {
                            onNetworkStateChanged(it)
                        }
                    })
                isFirst = false
            }, 120)
        }
    }

    private fun registerDefUIChange() {
        mViewModel.loadingChange.showDialog.observe(viewLifecycleOwner, Observer { showLoading() })
        mViewModel.loadingChange.dismissDialog.observe(viewLifecycleOwner,
            Observer { dismissDialog() })
    }

    /**
     * 将非该Fragment绑定的ViewModel添加 loading回调 防止出现请求时不显示 loading 弹窗bug
     * @param viewModels Array<out BaseViewModel>
     */
    private fun addLoadingObserve(vararg viewModels: BaseViewModel) {
        viewModels.forEach { viewModel ->
            //显示弹窗
            viewModel.loadingChange.showDialog.observe(viewLifecycleOwner,
                Observer { showLoading(it) })
            //关闭弹窗
            viewModel.loadingChange.dismissDialog.observe(viewLifecycleOwner,
                Observer { dismissDialog() })
        }
    }

    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    /**
     * 布局ID
     */
    abstract fun layoutId(): Int

    /**
     * 初始化View
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载
     */
    abstract fun lazyLoadData()

    /**
     * 创建观察者
     */
    abstract fun createObserver()

    /**
     * 网络变化监听 子类重写
     */
    abstract fun onNetworkStateChanged(netState: NetState)

    /**
     * fragment执行onCreate后触发的方法
     */
    open fun initData() {

    }

    abstract fun showLoading(message: String = "请求网络中...")

    abstract fun dismissDialog()

}