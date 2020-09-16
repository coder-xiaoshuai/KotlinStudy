package com.example.kotlinstudy.utils

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View


/**
 * mediaplayer播放器manager
 */
class MediaPlayerManager constructor(var context: Context, playView: SurfaceView) {

    private var mMediaPlayer: MediaPlayer? = null
    fun getMediaPlayer() = mMediaPlayer
    private var mSurfaceHolder: SurfaceHolder? = null

    init {
        mMediaPlayer = MediaPlayer()
        mSurfaceHolder = playView.holder
        mSurfaceHolder?.setKeepScreenOn(true)  //屏幕常亮
        mSurfaceHolder?.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder?) {
                Log.i("mediaplayer", "surfaceCreated:${mMediaPlayer?.isPlaying}")
                mMediaPlayer?.setDisplay(holder)
            }

            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                if (mMediaPlayer?.isPlaying == true) {
                    mMediaPlayer?.stop()
                }
                Log.i("mediaplayer", "surfaceDestroyed:${mMediaPlayer?.isPlaying}")
            }
        })

        //监听播放错误 发生错误隐藏surfaceView
        mMediaPlayer?.setOnErrorListener(MediaPlayer.OnErrorListener { mp, what, extra ->
            Log.i("mediaplayer", "setOnErrorListener  what:${what}")
            Log.i("mediaplayer", "setOnErrorListener  extra:${extra}")
//            when (what) {
//                MediaPlayer.MEDIA_ERROR_UNKNOWN,//未指定的错误
//                MediaPlayer.MEDIA_ERROR_SERVER_DIED //media server died，需要释放当前media player，创建一个新的mediaplayer
//                -> {
//                    playView.visibility = View.GONE
//                    return@OnErrorListener false
//                }
//            }
//            when (extra) {
//                MediaPlayer.MEDIA_ERROR_IO,//io错误，文件或者网络相关错误
//                MediaPlayer.MEDIA_ERROR_MALFORMED,//音视频格式错误，demux或解码错误
//                MediaPlayer.MEDIA_ERROR_UNSUPPORTED,//不支持的音视频格式
//                MediaPlayer.MEDIA_ERROR_TIMED_OUT,//操作超时，通常是超过了3—5秒
//                -2147483648//系统底层错误
//                -> {
//                    playView.visibility = View.GONE
//                    return@OnErrorListener false
//                }
//            }
            return@OnErrorListener true
        })
    }


    /**
     * @param filePath 包含文件名的相对路径
     */
    fun setAssetsPath(filePath: String) {
        try {
            val fd: AssetFileDescriptor = context.assets.openFd(filePath)
            mMediaPlayer?.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
            mMediaPlayer?.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING) //缩放模式
            mMediaPlayer?.prepareAsync()//异步准备
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * @param rawId 视频资源ID
     */
    fun setRawId(rawId: Int) {
        try {
            val fd: AssetFileDescriptor = context.resources.openRawResourceFd(rawId)
            mMediaPlayer?.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
            mMediaPlayer?.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING) //缩放模式
            mMediaPlayer?.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * 默认支持SD卡文件和网络加载
     */
    fun setDataSource(path: String) {
        try {
            mMediaPlayer?.setDataSource(path)
            mMediaPlayer?.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun setOnPreparedListener(listener: MediaPlayer.OnPreparedListener) {
        mMediaPlayer?.setOnPreparedListener(listener)
    }

    fun setOnInfoListener(listener: MediaPlayer.OnInfoListener) {
        mMediaPlayer?.setOnInfoListener(listener)
    }

    fun setOnCompletionListener(listener: MediaPlayer.OnCompletionListener) {
        mMediaPlayer?.setOnCompletionListener(listener)
    }

    fun setOnErrorListener(listener: MediaPlayer.OnErrorListener) {
        mMediaPlayer?.setOnErrorListener(listener)
    }


    fun startPlay() {
        Log.i("mediaplayer", "startPlay:${mMediaPlayer?.isPlaying}")
        if (mMediaPlayer?.isPlaying == false) {
            mMediaPlayer?.start()
            Log.i("mediaplayer", "startPlay:${mMediaPlayer?.isPlaying}")
        }
    }

    fun pausePlay() {
        if (mMediaPlayer?.isPlaying == true) {
            mMediaPlayer?.pause()
        }
    }

    fun releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer?.isPlaying == true) {
                mMediaPlayer?.stop()
            }
            mMediaPlayer?.release()
            mMediaPlayer = null
        }
    }
}