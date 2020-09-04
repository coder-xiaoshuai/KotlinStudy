package com.example.kotlinstudy.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.example.kotlinstudy.utils.MediaPlayerManager
import kotlinx.android.synthetic.main.activity_video_guide.*

class VideoGuideActivity2 : BaseActivity() {

    private var mediaPlayerManager:MediaPlayerManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAdapt()
        mediaPlayerManager = MediaPlayerManager(this, surfaceView)

        mediaPlayerManager?.setRawId(R.raw.login_bg)
        mediaPlayerManager?.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp -> mp?.start() })  //视频都是prepareAsync 所以需要在监听回调中开始播放视频

        mediaPlayerManager?.getMediaPlayer()?.isLooping = true  //设置循环播放
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_video_guide2
    }

    override fun initStatusBarColor() {
//        super.initStatusBarColor()
    }

    /**
     * 全屏显示：兼容刘海屏
     */
    private fun fullScreenAdapt() {
        try {
            val decorView = window.decorView
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }




    override fun onResume() {
        super.onResume()
        mediaPlayerManager?.startPlay()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayerManager?.pausePlay()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayerManager?.releaseMediaPlayer()
    }
    companion object {
        private const val TAG = "VideoGuideActivity"
    }
}