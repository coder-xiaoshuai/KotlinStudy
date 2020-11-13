package com.example.kotlinstudy.net

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import java.io.IOException
import java.net.HttpURLConnection
import java.net.NetworkInterface
import java.net.SocketException
import java.net.URL

object NetworkUtil {
    const val url = "https://www.baidu.com"

    /**
     * NetworkAvailable
     */
    const val NET_CNNT_BAIDU_OK = 1

    /**
     * no NetworkAvailable
     */
    const val NET_CNNT_BAIDU_TIMEOUT = 2

    /**
     * Net no ready
     */
    const val NET_NOT_PREPARE = 3

    /**
     * net error
     */
    const val NET_ERROR = 4

    /**
     * timeout
     */
    const val TIMEOUT = 3000

    /**
     * check NetworkAvailable
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun isNetworkAvailable(context: Context?): Boolean {
        val manager = context?.applicationContext?.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            ?: return false
        val info = manager.activeNetworkInfo
        return null != info && info.isAvailable
    }

    /**
     * getLocalIpAddress
     */
    @JvmStatic
    fun getLocalIpAddress(): String {
        var ret = ""
        try {
            val en =
                NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr =
                    intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress) {
                        ret = inetAddress.hostAddress.toString()
                    }
                }
            }
        } catch (exception: SocketException) {
            exception.printStackTrace()
        }
        return ret
    }

    /**
     * 返回当前网络状态
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getNetState(context: Context): Int {
        try {
            val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity != null) {
                val networkinfo = connectivity.activeNetworkInfo
                if (networkinfo != null) {
                    return if (networkinfo.isAvailable && networkinfo.isConnected) {
                        if (!connectionNetwork()) {
                            NET_CNNT_BAIDU_TIMEOUT
                        } else {
                            NET_CNNT_BAIDU_OK
                        }
                    } else {
                        NET_NOT_PREPARE
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return NET_ERROR
    }


    /**
     * ping "http://www.baidu.com"
     *
     * @return
     */
    private fun connectionNetwork(): Boolean {
        var result = false
        var httpUrl: HttpURLConnection? = null
        try {
            httpUrl = URL(url)
                .openConnection() as HttpURLConnection
            httpUrl.connectTimeout = TIMEOUT
            httpUrl!!.connect()
            result = true
        } catch (e: IOException) {
        } finally {
            httpUrl?.disconnect()
            httpUrl = null
        }
        return result
    }

    /**
     * check is3G
     *
     * @param context
     * @return boolean
     */
    @JvmStatic
    fun is3G(context: Context): Boolean {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return (activeNetInfo != null
                && activeNetInfo.type == ConnectivityManager.TYPE_MOBILE)
    }

    /**
     * isWifi
     *
     * @param context
     * @return boolean
     */
    @JvmStatic
    fun isWifi(context: Context): Boolean {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return (activeNetInfo != null
                && activeNetInfo.type == ConnectivityManager.TYPE_WIFI)
    }

    /**
     * is2G
     *
     * @param context
     * @return boolean
     */
    @JvmStatic
    fun is2G(context: Context): Boolean {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return (activeNetInfo != null
                && (activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_EDGE || activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_GPRS || activeNetInfo
            .subtype == TelephonyManager.NETWORK_TYPE_CDMA))
    }

}