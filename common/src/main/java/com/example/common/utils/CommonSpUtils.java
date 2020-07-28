package com.example.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author zhangshuai
 */
public class CommonSpUtils {
    private static SharedPreferences commonSp;
    private static SharedPreferences.Editor editor;
    private static final String SP_NAME = "common_sp";

    public static void init(Context context) {
        commonSp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }


    public static SharedPreferences.Editor getEditor() {
        if (editor == null) {
            editor = getCommonSp().edit();
        }
        return editor;
    }

    public static SharedPreferences getCommonSp() {
        if (commonSp == null) {
            commonSp = GlobalConfig.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return commonSp;
    }

    public static void putString(String key, String value) {
        getEditor().putString(key, value);
        getEditor().commit();
    }

    public static String getString(String key) {
        return getCommonSp().getString(key, "");
    }

    public static void putInt(String key, int value) {
        getEditor().putInt(key, value);
        getEditor().commit();
    }

    public static int getInt(String key) {
        return getCommonSp().getInt(key, 0);
    }

    public static void putLong(String key, int value) {
        getEditor().putLong(key, value);
        getEditor().commit();
    }

    public static long getLong(String key) {
        return getCommonSp().getLong(key, 0);
    }

    public static void putFloat(String key, float value) {
        getEditor().putFloat(key, value);
        getEditor().commit();
    }

    public static float getFloat(String key) {
        return getCommonSp().getFloat(key, 0f);
    }

    public static void putBoolean(String key, boolean value) {
        getEditor().putBoolean(key, value);
        getEditor().commit();
    }

    public static boolean getBoolean(String key) {
        return getCommonSp().getBoolean(key, false);
    }

    /**
     * 移除某项
     *
     * @param key
     */
    public static void remove(String key) {
        getEditor().remove(key);
        getEditor().commit();
    }

    /**
     * 清空全部数据
     */
    public static void clear() {
        getEditor().clear();
        getEditor().commit();
    }
}
