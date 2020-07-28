package com.example.kotlinstudy.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.common.utils.ViewUtils;


/**
 * @author zhangshuai
 */

public class DragFloatView extends LinearLayout {
    private int width = 480;
    private int height = 800;
    private int statusBarHeight;
    float startRawX, startRawY;
    float dX, dY;
    int lastAction;


    public DragFloatView(Context context) {
        super(context);
        init(context);
    }

    public DragFloatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragFloatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DragFloatView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(final Context context) {
        // 获取屏幕宽高
        width = ViewUtils.getScreenWidthPx();
        height = ViewUtils.getScreenHeightPx();
//        statusBarHeight = ViewUtils.getStatusBarHeight(context);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return handleTouch(event);
    }



    private boolean handleTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startRawX = event.getRawX();
                startRawY = event.getRawY();
                dX = getX() - startRawX;
                dY = getY() - startRawY;
                lastAction = MotionEvent.ACTION_DOWN;
                break;
            case MotionEvent.ACTION_MOVE:
                float currRawX = event.getRawX();
                float currRawY = event.getRawY();
                float x = currRawX + dX;
                float y = currRawY + dY;
                Log.i("zs","x="+(x < 0 ? 0 : (x > (width - getWidth()) ? (width - getWidth()) : x)));
                Log.i("zs","y="+(y < 0 ? 0 : (y > (height - getHeight() ) ? (height - getHeight() ) : y)));
                animate()
                        .x(x < 0 ? 0 : (x > (width - getWidth()) ? (width - getWidth()) : x))
                        .y(y < 0 ? 0 : (y > (height - getHeight() - statusBarHeight) ? (height - getHeight() - statusBarHeight) : y))
                        .setDuration(0)
                        .start();
                if (Math.abs(startRawX - currRawX) > 10 || Math.abs(startRawY - currRawY) > 10)
                    lastAction = MotionEvent.ACTION_MOVE;
                break;
            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_MOVE) {
                    int[] location = new int[2];
//                    findViewById(R.id.avatarViewPager).getLocationOnScreen(location);
//
//                    BaseLiveInviteDialog.Companion.setPoint(location[0], location[1]);

                    //intercept , don't pass to child
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }
}
