package com.example.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.example.common.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 统一封装 Toast
 *
 * @author zhangshuai
 */
public class ToastUtils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static long lastTime;//记录上一次的时间戳

    private ToastUtils() {
    }

    public static void init(Context context) {
        ToastUtils.context = context;
    }

    public static void show(String text) {
        ToastWrapper.INSTANCE.show(text, Gravity.BOTTOM);
    }

    public static void show(String text, int gravity) {
        ToastWrapper.INSTANCE.show(text, gravity);
    }


    public static void show(@StringRes int textResourceId) {
        String text = null;
        try {
            text = context.getString(textResourceId);
        } catch (Resources.NotFoundException exception) {
            exception.printStackTrace();
        }
        show(text);
    }

    public static void show(@StringRes int textResourceId, int gravity) {
        String text = null;
        try {
            text = context.getString(textResourceId);
        } catch (Resources.NotFoundException exception) {
            exception.printStackTrace();
        }
        show(text, gravity);
    }


//    public static void showToastInDelay(final String text, int delay) {
//        long currentTime = System.currentTimeMillis();
//        if (lastTime == 0) {
//            lastTime = currentTime;
//            MainThreadUtils.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    show(text);
//                }
//            }, delay);
//            return;
//        }
//        if (currentTime - lastTime >= delay) {
//            MainThreadUtils.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    show(text);
//                }
//            }, delay);
//            lastTime = currentTime;
//        }
//    }

    public static void show(@StringRes int textResourceId, Object... args) {
        String text = null;
        try {
            text = context.getString(textResourceId, args);
        } catch (Resources.NotFoundException exception) {
            exception.printStackTrace();
        }
        show(text);
    }

    enum ToastWrapper {
        @SuppressLint("StaticFieldLeak")
        INSTANCE;

        static final String TAG = ToastWrapper.class.getSimpleName();
        // System value paired with the flag Toast.LENGTH_SHORT.
        static final int DURATION_IN_MILLISECONDS = 1500;
        private static final int DURATION = Toast.LENGTH_SHORT;

        private static final int MSG_WHAT_SHOW = 1;
        private static final int MSG_WHAT_DISMISS = 2;

        TextView textView;

        final Handler mainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case MSG_WHAT_SHOW:
                        String text = (String) msg.obj;
                        // Ignore empty content or text equal to what is showing.
                        if (TextUtils.isEmpty(text) || (textView != null && text.equals(textView.getText()))) {
                            Log.d(TAG, "Ignore text: " + text);
                            return;
                        }
                        int gravity = msg.arg1 == 0 ? Gravity.CENTER : msg.arg1;
                        if (textView == null) {
                            createToastAndShow(text, gravity);
                            // Un-reference textView after toast dismisses.
                            mainHandler.sendEmptyMessageDelayed(MSG_WHAT_DISMISS, DURATION_IN_MILLISECONDS);
                            Log.d(TAG, "Show new toast: " + text);
                        } else {
                            textView.setText("");
                            textView.setText(text);
                            Log.d(TAG, "Update toast: " + text);
                        }
                        break;
                    case MSG_WHAT_DISMISS:
                        textView = null;
                        break;
                    default:
                        break;
                }
            }
        };

        void show(String text, int gravity) {
            Message toastMessage = mainHandler.obtainMessage(MSG_WHAT_SHOW, text);
            toastMessage.arg1 = gravity;
            toastMessage.sendToTarget();
        }

        void createToastAndShow(String text, int gravity) {
            Toast toast = new Toast(context);
            toast.setDuration(DURATION);
            textView = (TextView) ViewUtils.newInstance(context, R.layout.toast_layout_view);
            textView.setText(text);
            toast.setView(textView);
            int yOffset = 0;
            if (gravity == Gravity.BOTTOM) {
                yOffset = ViewUtils.dpToPx(30);
            }
            toast.setGravity(gravity, 0, yOffset);
            if (!SystemUtils.isNotificationEnabled(context)) {
                hookSystemToast(toast);
                return;
            }
            toast.show();
        }

        /**
         * fix没有通知权限不显示系统Toast
         */
        private void hookSystemToast(Toast toast) {
            try {
                @SuppressLint("SoonBlockedPrivateApi") Method getServiceMethod = Toast.class.getDeclaredMethod("getService");
                getServiceMethod.setAccessible(true);
                //hook INotificationManager
                final Object iNotificationManagerObj = getServiceMethod.invoke(null);

                Class iNotificationManagerCls = Class.forName("android.app.INotificationManager");
                Object iNotificationManagerProxy =
                        Proxy.newProxyInstance(toast.getClass().getClassLoader(), new Class[]{iNotificationManagerCls},
                                new InvocationHandler() {
                                    @Override
                                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                        //强制使用系统Toast
                                        if ("enqueueToast".equals(method.getName()) || "enqueueToastEx".equals(
                                                method.getName())) {  //华为p20 pro上为enqueueToastEx
                                            args[0] = "android";
                                        }
                                        return method.invoke(iNotificationManagerObj, args);
                                    }
                                });
                Field sServiceFiled = Toast.class.getDeclaredField("sService");
                sServiceFiled.setAccessible(true);
                sServiceFiled.set(null, iNotificationManagerProxy);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                toast.show();
            }
        }

    }
}
