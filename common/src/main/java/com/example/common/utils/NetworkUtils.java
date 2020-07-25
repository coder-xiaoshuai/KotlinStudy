package com.example.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * 网络工具类
 *
 * @author panxiangxing
 */
public class NetworkUtils {

    public enum NetworkType {
        NONE(0, ""), TYPE_WAP(1, "wap"), TYPE_2G(2, "2G"), TYPE_3G(3, "3G"), TYPE_4G(4, "4G"), TYPE_WIFI(5, "WIFI");

        private int value;
        private String typeName;

        public int getValue() {
            return value;
        }

        public String getTypeName() {
            return typeName;
        }

        NetworkType(int value, String typeName) {
            this.value = value;
            this.typeName = typeName;
        }

        public static String getNetworkName(int value) {
            for (NetworkType networkType : NetworkType.values()) {
                if (networkType.getValue() == value) {
                    return networkType.getTypeName();
                }
            }
            return NONE.getTypeName();
        }

        public static NetworkType getNetworkType(int value) {
            for (NetworkType networkType : NetworkType.values()) {
                if (networkType.getValue() == value) {
                    return networkType;
                }
            }
            return NONE;
        }
    }

    /**
     * 检测 WiFi 是否打开
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * 检测网络是否连接
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isConnectedOrConnecting();
    }

    public static boolean isWifiConnected(Context context) {
        return NetworkType.TYPE_WIFI.getValue() == getNetworkType(context);
    }

    /**
     * 返回当前网络类型
     */
    public static int getNetworkType(Context context) {
        if (context == null) {
            return NetworkType.NONE.getValue();
        }
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return NetworkType.NONE.getValue();
        }
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();

            if (type.equalsIgnoreCase("WIFI")) {
                return NetworkType.TYPE_WIFI.getValue();
            } else if (type.equalsIgnoreCase("MOBILE")) {
                String strSubTypeName = networkInfo.getSubtypeName();
                switch (networkInfo.getSubtype()) {
                    //如果是2g类型
                    case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                    case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                    case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NetworkType.TYPE_2G.getValue();
                    //如果是3g类型
                    case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NetworkType.TYPE_3G.getValue();
                    //如果是4g类型
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NetworkType.TYPE_4G.getValue();
                    default:
                        //中国移动 联通 电信 三种3G制式
                        if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA")
                            || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            return NetworkType.TYPE_3G.getValue();
                        } else {
                            return NetworkType.TYPE_WAP.getValue();
                        }
                }
            }
        }
        return NetworkType.NONE.getValue();
    }

    public static String getIp() {
        String localIP = "NONE";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface networkInterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddress = networkInterface.getInetAddresses();
                    enumIpAddress.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddress.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        //fix xiaomi get ip error
                        if (networkInterface.getName().startsWith("usbnet")) {
                            continue;
                        }
                        if (inetAddress instanceof Inet6Address) continue;
                        localIP = inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            Log.e("SystemUtils", "getLocalIpAddress Exception:" + e.toString());
        }
        return localIP;
    }

    public static String getMacAddress(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return getMacAddressAfterMarshmallow();
            } else {
                WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                return wifiMan.getConnectionInfo().getMacAddress();
            }
        } catch (Exception e) {
            Log.e("SystemUtils", "get MAC address Exception:" + e.toString());
            return "";
        }
    }

    /**
     * http://stackoverflow.com/questions/33103798/how-to-get-wi-fi-mac-address-in-android-marshmallow
     */
    private static String getMacAddressAfterMarshmallow() throws SocketException {
        List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
        for (NetworkInterface nif : all) {
            if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

            byte[] macBytes = nif.getHardwareAddress();
            if (macBytes == null) {
                return "";
            }

            StringBuilder res1 = new StringBuilder();
            for (byte b : macBytes) {
                res1.append(Integer.toHexString(b & 0xFF)).append(":");
            }

            if (res1.length() > 0) {
                res1.deleteCharAt(res1.length() - 1);
            }
            return res1.toString();
        }
        return "";
    }

    public static String getDns() {
        try {
            Class<?> SystemProperties = Class.forName("android.os.SystemProperties");
            Method method = SystemProperties.getMethod("get", String.class);
            for (String name : new String[] { "net.dns1", "net.dns2", "net.dns3", "net.dns4", }) {
                String value = (String) method.invoke(null, name);
                if (!TextUtils.isEmpty(value)) return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 异步操作, 需要在异步线程中调用
     * 根据url获取域名对应的ip地址
     */
    public static String getIpByUrl(String url) {
        try {
            InetAddress ipAddress = InetAddress.getByName(new URL(url).getHost());
            return ipAddress.getHostAddress();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }
}
