package com.example.common_ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.common.utils.GlobalConfig;
import com.example.common_ui.R;
import com.jaeger.library.StatusBarUtil;

/**
 * 基础的 activity，内部除了做 fragment 的初始化，剩下不做任何业务（当然底层肯定还会记录日志），保证所有 activity 继承时候的纯净。
 *
 * @author zhangshuai
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected BaseFragment fragment;

    private static final int DEFAULT_ALPHA = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
//        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColor(this, getStatusBarColor(), DEFAULT_ALPHA);
        GlobalConfig.setCurrentActivity(this);
    }

    protected boolean fitsSystemWindows() {
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (fragment != null) {
            fragment.onNewIntent(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void replaceFragment(Fragment newFragment) {
        replaceFragment(newFragment, null, false);
    }

    protected void replaceFragment(Fragment newFragment, Bundle arguments, boolean isAddStack) {
        if (isFinishing()) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (arguments != null) {
            newFragment.setArguments(arguments);
        }
        transaction.replace(R.id.ui_framework_fragment_container, newFragment);
        if (isAddStack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 获得 layout id
     */
    protected int getLayoutId() {
        return R.layout.ui_framework_activity_base;
    }

    protected int getStatusBarColor() {
        return ContextCompat.getColor(this, R.color.white);
    }

    /**
     * 获得当前的 fragment 主要用于在 fragment 里面接受 onActivityResult
     */
    public BaseFragment getFragment() {
        return fragment;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (fragment != null) {
            if (fragment.onKeyDown(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (fragment != null && fragment.isCreated()) {
            fragment.onBackPress();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        if (fragment != null) {
            fragment.onFinish();
        }
        super.finish();
        finishWithAnim();
    }

    protected void finishWithAnim() {
//        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
