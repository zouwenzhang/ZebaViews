package com.zeba.views.click;

import android.view.MotionEvent;

public interface ViewSuperCallBack {
    boolean superPerformClick();
    boolean superPerformLongClick();
    boolean superOnTouchEvent(MotionEvent event);
}
