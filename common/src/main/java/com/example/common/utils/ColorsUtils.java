package com.example.common.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;

import androidx.annotation.ColorInt;

/**
 * 颜色相关工具类
 *
 * @author zhangshuai
 */
public class ColorsUtils {

    private ColorsUtils() {}

    /**
     * 将服务器下发的 Feed 占位颜色转换
     */
    public static int feedBgColor(String photoHue) {
        String defaultColor = "#EEECEA";
        if (TextUtils.isEmpty(photoHue)) {
            return Color.parseColor(defaultColor);
        }
        String hexColor = photoHue.replace("0x", "#");
        try {
            return Color.parseColor(hexColor);
        } catch (Exception e) {
            return Color.parseColor(defaultColor);
        }
    }

    /**
     * 检查 bitmap 是否全为一个颜色
     */
    public static boolean checkSingleColor(Bitmap bitmap, @ColorInt int targetColor) {
        for (int i = 0; i < bitmap.getHeight(); i++) {
            for (int j = 0; j < bitmap.getWidth(); j++) {
                int color = bitmap.getPixel(j, i);
                int rgbColor = Color.rgb(Color.red(color), Color.green(color), Color.blue(color));
                if (rgbColor != targetColor) {
                    return false;
                }
            }
        }
        return true;
    }
}
