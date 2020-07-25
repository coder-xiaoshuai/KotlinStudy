package com.example.common.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;

/**
 * 设备、应用信息
 *
 * @author zhangshuai
 */
public class SystemUtils {

    private static final String MAIL_TO_PREFIX = "mailto";
    private static final int PAD_MIN_WIDTH_DP = 600;
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    private static final int DEVICE_ID_LENGTH_BEFORE_HASH = 32;

    private SystemUtils() {}

    /**
     * 获取应用版本
     */
    public static String getAppVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo == null) {
            return "";
        }

        return packageInfo.versionName;
    }

    public static int getAppVersionCode(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo == null) {
            return -1;
        }

        return packageInfo.versionCode;
    }

    /**
     * Get app version information with versionName and versionCode, like v2.12.0-build2700.
     */
    public static String getAppVersionInfo(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo == null) {
            return "";
        }

        return "v" + packageInfo.versionName + "-build" + packageInfo.versionCode;
    }

    private static boolean isGrayBetaVersionInner(Context context) {
        String appVersion = getAppVersionName(context);
        String[] versions = appVersion.split("\\.");
        if (versions.length < 3) {
            return false;
        }

        try {
            Integer intVersion = Integer.valueOf(versions[2]);
            return intVersion >= 5 && intVersion < 9;
        } catch (NumberFormatException ignore) {
            return false;
        }
    }

    /**
     * 返回设备是否是 Android Pad
     * 认为宽度大于等于 600dp 的是 Pad
     */
    public static boolean isAndroidPad(Context context) {
        return ViewUtils.getScreenWidthDp(context) >= PAD_MIN_WIDTH_DP;
    }

    /**
     * 返回设备是否有 GPS
     */
    public static boolean hasGPSProvider(Context context) {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (manager == null) {
            return false;
        }
        final List<String> providers = manager.getAllProviders();
        return providers != null && providers.contains(LocationManager.GPS_PROVIDER);
    }

    /**
     * 打开系统的 Flow 设置页面
     */
    public static void openApplicationDetailsSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开系统的 Flow 设置页面
     */
    public static void openApplicationDetailsSettings(Fragment fragment, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", fragment.getActivity().getPackageName(), null);
        intent.setData(uri);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 生成邮件
     */
    public static void makeEmail(Context context, String mailTo, String body, String chooserTitle) {
        Uri uri = Uri.fromParts(MAIL_TO_PREFIX, mailTo, null);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(emailIntent, chooserTitle));
    }

    /**
     * 获取当前进程名称
     */
    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infoList = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : CollectionUtils.notNull(infoList)) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return "";
    }

    /**
     * 返回指定包名的应用是否已安装
     */
    public static boolean isPackageInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return (packageInfo != null);
    }

    /**
     * 打开指定包名的 app
     */
    public static void openPackage(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 复制文本到剪切板
     */
    public static void copyTextToClipboard(Context context, String label, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard == null) {
            return;
        }
        ClipData clipData = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clipData);
    }

    /**
     * 获取剪切板字符串
     */
    public static String getTextFromClipboard(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard == null) {
            return "";
        }
        ClipData clipData = clipboard.getPrimaryClip();
        if (clipData == null || clipData.getItemCount() == 0) {
            return "";
        }
        return clipData.getItemAt(0).getText().toString();
    }

    public static String getEncodeModel() {
        return encodeString(returnUnknownWhenEmpty(Build.MODEL));
    }

    public static boolean canIntentBeHandled(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * Return the model of device.
     * <p>e.g. MI2SC</p>
     *
     * @return the model of device
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    /**
     * Return the version name of device's system.
     *
     * @return the version name of device's system
     */
    public static String getSDKVersionName() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 调用系统的安装界面
     *
     * @param filePath 安装 apk 的文件路径
     */
    public static void installAPKBySystemAPI(Context context, String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file =
            new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS, "flow.apk");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(context, "com.flowsns.flow.fileProvider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * 获取电话号码
     */
    @SuppressLint("MissingPermission")
    public static String getPhoneNumber() {
        try {
            TelephonyManager telephonyManager =
                (TelephonyManager) GlobalConfig.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager == null) {
                return "";
            }
            String number = telephonyManager.getLine1Number();
            return TextUtils.isEmpty(number) ? "" : number;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 检测是否在用耳机
     * 有线耳机和蓝牙耳机，都会返回 true
     */
    public static boolean isHeadsetOn(Context context) {
        try {
            AudioManager localAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            return (localAudioManager != null) && (localAudioManager.isWiredHeadsetOn()
                || localAudioManager.isBluetoothA2dpOn());
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * 是否为 Android 7.0 或 Android 7.1
     */
    public static boolean isAndroid7() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.N || Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1;
    }

    /**
     * 获取app当前的渠道号或application中指定的meta-data
     */
    public static String getAppMetaData(String key) {
        String channelNumber = "flow";
        if (TextUtils.isEmpty(key)) {
            return channelNumber;
        }
        try {
            PackageManager packageManager = GlobalConfig.getContext().getPackageManager();
            if (packageManager == null) {
                return channelNumber;
            }
            ApplicationInfo applicationInfo =
                packageManager.getApplicationInfo(GlobalConfig.getContext().getPackageName(),
                    PackageManager.GET_META_DATA);
            if (applicationInfo != null && applicationInfo.metaData != null) {
                channelNumber = applicationInfo.metaData.getString(key);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelNumber;
    }

    /**
     * 判断手机是否是处于静音模式
     */
    public static boolean isSilentMode() {
        AudioManager audioManager = (AudioManager) GlobalConfig.getContext().getSystemService(Context.AUDIO_SERVICE);
        return audioManager != null && audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0;
    }

    /**
     * 获取系统息屏时间
     */
    public static long getScreenOffTime() {
        long screenOffTime = Integer.MAX_VALUE;
        try {
            screenOffTime = Settings.System.getInt(GlobalConfig.getContext().getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenOffTime;
    }

    /**
     * 设置系统息屏时间
     */
    public static void setScreenOffTime(long paramInt) {
        try {
            Settings.System.putLong(GlobalConfig.getContext().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,
                paramInt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断应用的通知栏权限是否打开
     */
    public static boolean isNotificationEnabled(Context context) {
        AppOpsManager appOpsManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        }
        if (appOpsManager == null) {
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        }
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String packageName = context.getApplicationContext().getPackageName();
        int uid = applicationInfo.uid;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return isNotificationEnabledV26(context, packageName, uid);
        }

        try {
            Class<?> appOpsClass = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                appOpsClass = Class.forName(AppOpsManager.class.getName());
            }
            Method checkOpNoThrowMethod =
                appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (int) opPostNotificationValue.get(Integer.class);
            return ((int) checkOpNoThrowMethod.invoke(appOpsManager, value, uid, packageName)
                == AppOpsManager.MODE_ALLOWED) || NotificationManagerCompat.from(context).areNotificationsEnabled();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }

    /**
     * 获取设备的标识
     */
    public static String getDeviceId(Context context) {
        String res;
        String imei = getIMEI(context);
        String mac = NetworkUtils.getMacAddress(context);
        if (TextUtils.isEmpty(imei)) {
            res = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID) + (
                TextUtils.isEmpty(mac) ? "" : mac.replace(":", ""));
        } else {
            res = TextUtils.isEmpty(imei) ? "" : imei + (TextUtils.isEmpty(mac) ? "" : mac.replace(":", ""));
        }
        if (res.length() < DEVICE_ID_LENGTH_BEFORE_HASH) {
            StringBuilder stringBuilder = new StringBuilder(res);
            for (int length = res.length(); length < DEVICE_ID_LENGTH_BEFORE_HASH; length++) {
                stringBuilder.append("0");
            }
            return stringBuilder.toString();
        } else if (res.length() == DEVICE_ID_LENGTH_BEFORE_HASH) {
            return res;
        } else {
            return res.substring(0, DEVICE_ID_LENGTH_BEFORE_HASH);
        }
    }

    private static boolean isNotificationEnabledV26(Context context, String packageName, int uid) {
        try {
            NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Method sServiceField = notificationManager.getClass().getDeclaredMethod("getService");
            sServiceField.setAccessible(true);
            Object sService = sServiceField.invoke(notificationManager);

            Method method =
                sService.getClass().getDeclaredMethod("areNotificationsEnabledForPackage", String.class, Integer.TYPE);
            method.setAccessible(true);
            return (boolean) method.invoke(sService, packageName, uid);
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 调节系统音量，当页面重写 onKeyDown， 并且页面允许调节音量的时候调用
     */
    public static boolean adjustVolumeWhenOnKeyDown(int keyCode) {
        AudioManager audioManager = (AudioManager) GlobalConfig.getContext().getSystemService(Context.AUDIO_SERVICE);
        if (audioManager == null) {
            return false;
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE,
                    AudioManager.FX_FOCUS_NAVIGATION_UP);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER,
                    AudioManager.FX_FOCUS_NAVIGATION_UP);
                return true;
        }
        return false;
    }

    /**
     * 得到应用包的信息
     */
    private static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_GIDS);
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return null;
    }

    private static String returnUnknownWhenEmpty(String value) {
        return value == null ? "Unknown" : value;
    }

    private static String encodeString(String value) {
        String res = "";
        try {
            res = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String getIMEI(Context context) {
        try {
            String firstImei = getIMEI(context, 0);
            if (!TextUtils.isEmpty(firstImei)) {
                return firstImei;
            }
            return getIMEI(context, 1);
        } catch (Exception e) {
            return "";
        }
    }

    private static String getIMEI(Context context, int slotId) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (manager == null) {
                return "";
            }
            Method method = manager.getClass().getMethod("getImei", int.class);
            return (String) method.invoke(manager, slotId);
        } catch (Exception e) {
            return "";
        }
    }

    public static void setStatusBarColor(Window window, @ColorInt int color) {
        if (window == null) {
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ColorUtils.blendARGB(color, Color.TRANSPARENT, 0f));
            View decorView = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
        }
    }

    public static void setStreamMute(boolean enable) {
        AudioManager mgr = (AudioManager) GlobalConfig.getContext().getSystemService(Context.AUDIO_SERVICE);
        if (mgr != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mgr.adjustStreamVolume(AudioManager.STREAM_SYSTEM,
                    enable ? AudioManager.ADJUST_MUTE : AudioManager.ADJUST_UNMUTE, 0);
            } else {
                mgr.setStreamMute(AudioManager.STREAM_SYSTEM, enable);
            }
        }
    }

    /**
     * 调起系统发短信功能
     * @param phoneNumber 发送短信的接收号码
     * @param message     短信内容
     */
    public static void sendSMS(Fragment fragment, String phoneNumber, String message, int requestCode) {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
        smsIntent.putExtra("sms_body", message);
        smsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        fragment.startActivityForResult(smsIntent, requestCode);
    }
}
