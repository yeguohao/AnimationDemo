package com.ygh.animationdemo;

import android.animation.TimeInterpolator;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.LinearInterpolator;

public class Anim {

    private int start;
    private int end;
    private double unit;
    private long duration = 300;
    private long startTime;
    private long endTime;
    private boolean cancel;
    private TimeInterpolator interpolator = new LinearInterpolator();

    private Handler handler = new Handler(Looper.getMainLooper());

    private Listener listener;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (cancel) {
                return;
            }
            long curTime = System.currentTimeMillis();
            if (curTime > endTime) {
                calculate(duration);
                return;
            }

            calculate((curTime - startTime));
            handler.post(runnable);
        }
    };

    private void calculate(long time) {
        float l = interpolator.getInterpolation(time);
        int r = (int) (l * unit);

        if (listener != null) {
            listener.update(r);
            if (l == duration) {
                listener.end();
            }
        }
    }

    private Anim() {}

    public static Anim rangeInt(int start, int end) {
        Anim anim = new Anim();
        anim.start = start;
        anim.end = end;
        return anim;
    }

    public Anim duration(long duration) {
        this.duration = duration;
        return this;
    }

    private float to2(float a) {
        return (float)(Math.round(a * 100)) / 100;
    }

    public Anim interpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public Anim listener(Listener listener) {
        this.listener = listener;
        return this;
    }

    public void start() {
        unit = to2(((float) end - (float) start) / duration);
        startTime = System.currentTimeMillis();
        endTime = startTime + duration;
        handler.post(runnable);
    }

    public void cancel() {
        cancel = true;
    }

    public interface Listener {
        void update(int val);
        void end();
    }

}
