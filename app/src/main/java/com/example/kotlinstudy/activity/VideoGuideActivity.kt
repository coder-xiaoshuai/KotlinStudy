package com.example.kotlinstudy.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import kotlinx.android.synthetic.main.activity_video_guide.*

@Deprecated("已过时，可以参考VideoGuideActivity2")
class VideoGuideActivity : BaseActivity() {

    private var mMediaPlayer: MediaPlayer? = null
    private var currentPosition: Int = -1
    private var mSurfaceHolder: SurfaceHolder? = null
    private var isSHCreate = false  //SurfaceHolder是否创建

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAdapt()
        initMediaPlayer()
        initSurface()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_video_guide
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

    private fun initMediaPlayer() {
        mMediaPlayer = MediaPlayer()
        mMediaPlayer?.setVolume(0f, 0f)//设置静音
        val file = resources?.openRawResourceFd(R.raw.login_bg) ?: return
        mMediaPlayer?.setDataSource(file.fileDescriptor, file.startOffset, file.length)
        mMediaPlayer?.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT) //缩放模式
        mMediaPlayer?.isLooping = true //设置循环播放
        mMediaPlayer?.prepareAsync() //异步准备
        mMediaPlayer?.setOnPreparedListener { mp ->
            Log.i(TAG, "onPrepared")
            mp.start()
        }

        mMediaPlayer?.setOnInfoListener(object : MediaPlayer.OnInfoListener {
            override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                Log.i(TAG, "onInfo")
                return false
            }
        })

        mMediaPlayer?.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
            override fun onCompletion(mp: MediaPlayer?) {
                Log.i(TAG, "onCompletion")
            }
        })

    }

    private fun initSurface() {
        mSurfaceHolder = surfaceView.holder
        mSurfaceHolder?.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
                Log.i(TAG, "surfaceChanged")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                Log.i(TAG, "surfaceDestroyed")
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                Log.i(TAG, "surfaceCreated")
                mMediaPlayer?.setDisplay(holder)
                if (!isSHCreate) {
                    initMediaPlayer()
                    isSHCreate = true
                }
            }
        })
    }


    override fun onResume() {
        super.onResume()
        if (mMediaPlayer?.isPlaying == false) {
            Log.i(Companion.TAG, "position onresume${mMediaPlayer?.currentPosition}")
            mMediaPlayer?.start()
        }
    }

    override fun onPause() {
        super.onPause()
        currentPosition = mMediaPlayer?.currentPosition ?: -1
        Log.i(Companion.TAG, "position onpause$currentPosition")
        mMediaPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseMediaPlayer()
    }

    private fun releaseMediaPlayer() {
        if (mMediaPlayer?.isPlaying == true) {
            mMediaPlayer?.stop()
        }
//        mMediaPlayer?.setOnPreparedListener(null)
        mMediaPlayer?.release()
        mMediaPlayer = null
    }

    companion object {
        private const val TAG = "VideoGuideActivity"
    }
}