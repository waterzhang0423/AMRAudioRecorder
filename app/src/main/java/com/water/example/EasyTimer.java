package com.water.example;

import android.os.Handler;

/**
 * Created by Chris on 12/9/13.
 */
public class EasyTimer {
    private Handler handler;
    private Runnable runnable;
    private long delayInterval;
    private CallBack callBack;

    public static interface CallBack
    {
        public void execute();
    }

    public EasyTimer(final long millionSeconds, final CallBack callBack) {
        this.delayInterval = millionSeconds;
        this.callBack = callBack;
        start();
    }

    public void stop  () {
        handler.removeCallbacks(runnable);
    }

    public void restart () {
        stop();
        start();
    }

    private void start () {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                callBack.execute();
                handler.postDelayed(runnable,delayInterval);
            }
        };
        handler.postDelayed(runnable,delayInterval);
    }
}
