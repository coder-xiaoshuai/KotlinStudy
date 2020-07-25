package com.example.common_ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 所有 fragment 的基类，目前只做声明周期的规范，不做其他任何业务
 *
 * @author zhangshuai
 */
public abstract class BaseFragment extends Fragment {

    protected View contentView;
    protected boolean inflated;
    private boolean isFragmentCreated = false;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        contentView = inflater.inflate(getLayoutResId(), container, false);
        return contentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isFragmentCreated = true;
        if (contentView != null) {
            onInflated(contentView, savedInstanceState);
            inflated = true;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFragmentCreated = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onNewIntent(Intent intent) {}

    public void onFinish() {}

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public void onBackPress() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            getActivity().finish();
        }
    }

    /**
     * @return 当前 fragment 的 layout id
     */
    protected abstract int getLayoutResId();

    /**
     * 当 fragment inflated 结束的时候调用
     */
    protected abstract void onInflated(View contentView, Bundle savedInstanceState);

    /**
     * 给外部提供 fragment 的 root view。继承类可以直接使用 {@link BaseFragment#contentView}。
     * 这里需要注意的是，在 fragment onDetached 之后这个返回值可能为 null
     */
    public View getContentView() {
        return contentView;
    }

    protected <T extends View> T findViewById(int resId) {
        return (T) contentView.findViewById(resId);
    }

//    /**
//     * 当配合 {@link BaseTitleActivity} 使用时，可以方便的在 fragment 中获得 title 的实例，并操作。
//     */
//    public CustomTitleBarItem getTitleBar() {
//        if (getActivity() instanceof BaseTitleActivity) {
//            return ((BaseTitleActivity) getActivity()).getTitleBar();
//        }
//        return null;
//    }
//
//    protected void showProgressDialog(String messageText) {
//        if (getActivity() == null || getActivity().isFinishing()) {
//            return;
//        }
//        if (progressBarDialog != null) {
//            return;
//        }
//        progressBarDialog =
//            new FlowProgressBarDialog.Builder(getActivity()).message(messageText).cancelable(false).build();
//        progressBarDialog.show();
//    }

    public boolean isCreated() {
        return isFragmentCreated;
    }

}
