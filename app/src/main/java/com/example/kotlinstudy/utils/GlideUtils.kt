package com.example.kotlinstudy.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.kotlinstudy.R

/**
 * glide加载工具类
 *
 * @author zhangshuai
 */
object GlideUtils {

    fun loadNormal(context: Context, imageView: ImageView, imageUrl: String) {
        load(context, imageView, imageUrl, R.drawable.shape_placeholder_normal_default, R.drawable.shape_placeholder_normal_default)
    }

    fun loadNormal(context: Context, imageView: ImageView, imageUrl: String, placeHolderResId: Int) {
        load(context, imageView, imageUrl, placeHolderResId, R.drawable.shape_placeholder_normal_default)
    }

    fun loadNormal(context: Context, imageView: ImageView, imageUrl: String, placeHolderResId: Int, errorResId: Int) {
        load(context, imageView, imageUrl, placeHolderResId, errorResId)
    }

    /**
     * 正常加载图片
     *
     * @param context
     * @param imageView
     * @param url
     * @param placeHolderResId
     * @param errorResId
     */
    private fun load(context: Context, imageView: ImageView, url: String, placeHolderResId: Int, errorResId: Int) {
        Glide.with(context)
            .load(url)
            .centerCrop()
            .placeholder(placeHolderResId)
            .error(errorResId)
            .into(imageView)
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param imageView
     * @param url
     * @param placeHolderResId
     * @param errorResId
     */
    fun loadCircle(context: Context, imageView: ImageView, url: String, placeHolderResId: Int = R.drawable.shape_placeholder_circle_default, errorResId: Int = R.drawable.shape_placeholder_circle_default) {
        Glide.with(context)
            .load(url)
            .centerCrop()
            .placeholder(placeHolderResId)
            .error(errorResId)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(imageView)
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param imageView
     * @param url
     * @param radius
     * @param placeHolderResId
     * @param errorResId
     */
    fun loadRound(context: Context, imageView: ImageView, url: String, radius: Int, placeHolderResId: Int = R.drawable.shape_placeholder_normal_default, errorResId: Int = R.drawable.shape_placeholder_normal_default) {
        Glide.with(context)
            .load(url)
            .centerCrop()
            .placeholder(placeHolderResId)
            .error(errorResId)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(radius))).into(imageView)
    }
}