package com.water.example;

import android.os.Handler;

/**
 * A convenient way to use timer.
 *
 * Created by Water Zhang on 12/9/13.
 */
public class EasyTimer {
    private Handler handler;
    private Runnable runnable;
    private long delayInterval;
    private CallBack callBack;
    private boolean stopped;

    public interface CallBack {
        void execute();
    }

    public EasyTimer (final long millionSeconds,final CallBack callBack) {
        this.delayInterval = millionSeconds;
        this.callBack = callBack;
        start();
    }

    public void stop  () {
        stopped = true;
        handler.removeCallbacksAndMessages(null);
    }

    public void restart () {
        stop();
        start();
    }

    private void start () {
        stopped = false;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (!stopped) {
                    callBack.execute();
                    handler.postDelayed(runnable,delayInterval);
                }
            }
        };
        handler.postDelayed(runnable,delayInterval);
    }
}
