package com.example.common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * 维护全局使用的参数，目前只看到的是 context, activity
 *
 * @author zhangshuai
 */
public class GlobalConfig {

    // 存储的是 application context 不会内存泄露
    @SuppressLint("StaticFieldLeak") private static Context context;

    // 弱引用持有当前，不会引起内存泄露
    private static WeakReference<Activity> currentActivity;

    private GlobalConfig() {
    }

    /**
     * 这里得到 application 的 context， 目的是为了在 framework 层使用，而不需每次都从上层使用参数下传
     */
    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        GlobalConfig.context = context;
    }

    /**
     * 设置当前 activity，只在基类中调用，开发者不要使用这个接口。
     * 没有保内可见是因为现在结构还没调整基类混乱。
     */
    public static void setCurrentActivity(Activity activity) {
        currentActivity = new WeakReference(activity);
    }

    /**
     * 获得当前的 activity
     */
    public static Activity getCurrentActivity() {
        return currentActivity != null ? currentActivity.get() : null;
    }

    public static void safelyFinishCurrentActivity() {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    /**
     * 获取当前应用包名
     */
    public static String getPackageName() {
        if (context != null) {
            return context.getPackageName();
        }
        return null;
    }

    /**
     * 从给定的 view 向上寻找 activity 的 context。
     * 使用场景，有时候需要找到 activity 的引用，然而有时候 view 直接 getContent 并不是 Activity ，而是 {@link ContextWrapper} 的另外的子类
     */
    public static Activity findActivity(View view) {
        if (view == null) {
            return null;
        }
        return getActivityFromContext(view.getContext());
    }

    @Nullable
    public static Activity getActivityFromContext(Context context) {
        //如果传入的Context本身就是Activity的Context,那么 getBaseContext 可能拿到 ContextIml 导致返回null
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }
}
