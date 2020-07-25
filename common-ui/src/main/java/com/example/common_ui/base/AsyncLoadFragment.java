package com.example.common_ui.base;

import android.os.Bundle;
import android.view.ViewStub;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;

import com.example.common_ui.R;


/**
 * 所有需要从网络加载数据的 fragment 都需要继承这个 fragment，这个 fragment 规范了网络加载的位置，
 * 当 fragment 被放入 {@link androidx.viewpager.widget.ViewPager} 等可以滚动的 container 时（或者一些零流量的场景），
 * 我们可以统一的关闭网络加载，保证界面的流畅性。
 *
 * @author panxiangxing
 */
public abstract class AsyncLoadFragment extends BaseFragment {

    private static final String KEY_ALLOW_LOAD = "allow-load";

    private boolean allowLoading = true;
    private boolean viewStubInflate = false;
    private boolean loaded = false;

    private boolean lazyLoad = false;
    private boolean isViewInitiated;
    private boolean isVisibleToUser;
    private boolean isDataInitiated;

    private Bundle savedInstanceState;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_ALLOW_LOAD, allowLoading);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setLazyLoad(lazyLoad);
    }

    protected void setLazyLoad(boolean lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    @Override
    public final void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;

        if (lazyLoad) {
            prepareFetchData(true);
            return;
        }
        handleActivityCreated(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData(isVisibleToUser);
    }

    /**
     * 使用者如果想动态的阻止网络访问可以重写这个方法。
     *
     * @return true 允许网络加载, false 不允许
     */
    protected boolean needToLoadData() {
        return inflated && isAdded();
    }

    /**
     * 在网络加载前调用，使用者在这里做 viewStub 内部 id 的 find，此函数在 fragment 生命周期中下只会调用一次
     */
    protected void onViewStubInflate() {
        ViewStub viewStub = contentView.findViewById(R.id.view_stub);
        if (viewStub != null) {
            viewStub.inflate();
        }
    }

    /**
     * 在网络加载前调用，使用者可以在这里 show 一些阻塞用户操作的 dialog 或者 tips
     * <p>
     * <b>不要在这里加载数据</b>
     * </p>
     */
    protected void onPrepareLoading() {
    }

    /**
     * 使用者重写这个方法，在里面添加网络加载的操作，此函数在 fragment 生命周期中下只会调用一次。除非手动调用 {@link #requestLoad()}
     */
    protected abstract void onStartLoading();

    /**
     * 调用这个方法，将会重新触发加载流程。
     * 一般来说使用者不要重写这个方法，除非你要手动的刷新整个界面.
     * 继承这个方法必须先使用父类的实现
     */
    @CallSuper
    protected void requestLoad() {
        if (!needToLoadData()) {
            return;
        }
        onPrepareLoading();
        if (allowLoading) {
            loaded = true;
            onStartLoading();
        }
    }

    /**
     * 这个方法可以设置是否允许这个 fragment 访问网络，例如在滑动时调用这个方法，并设置 false
     */
    public final void setAllowLoading(boolean allowLoading) {
        this.allowLoading = allowLoading;
        if (allowLoading && !loaded && inflated) {
            inflateViewStub();
            requestLoad();
        }
    }

    /**
     * 获取现在是否允许访问网络
     */
    public final boolean isAllowLoading() {
        return allowLoading && isAdded();
    }

    private void prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            handleActivityCreated(savedInstanceState);
            isDataInitiated = true;
        }
    }

    private void handleActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            allowLoading = savedInstanceState.getBoolean(KEY_ALLOW_LOAD);
        }
        if (!needToLoadData()) {
            return;
        }
        // 这里 post 的原因是为了保证 {@link android.support.v4.view.ViewPager} 的状态在 requestLoad 前设置完毕
//        MainThreadUtils.post(() -> {
//            // 因为是 post 所以需要再次检查下 fragment 的状态
//            if (!isAdded()) {
//                return;
//            }
//            // 允许加载且 viewStub 还没有 init
//            if (!viewStubInflate && allowLoading) {
//                // 初始化 viewStub
//                viewStubInflate = true;
//                onViewStubInflate();
//            }
//            // 没有加载过
//            if (loaded) {
//                return;
//            }
//            onPrepareLoading();
//            if (allowLoading) {
//                loaded = true;
//                onStartLoading();
//            }
//        });
    }

    /**
     * 设置 viewStub 初始化
     */
    private void inflateViewStub() {
        if (viewStubInflate) {
            return;
        }
        viewStubInflate = true;
        onViewStubInflate();
    }
}
