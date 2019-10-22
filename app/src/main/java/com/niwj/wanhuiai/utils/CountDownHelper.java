package com.niwj.wanhuiai.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by prince70 on 2019/9/26.
 */
public class CountDownHelper {
    //    倒计时
    private CountDownTimer countDownTimer;


    //    倒计时结束的回调接口
    private OnFinishListener listener;
    private TextView textView;
    /**
     *
     * @param textView
     * 需要显示倒计时的TextView
     * @param defaultString
     * 默认显示的字符串
     * @param max
     * 需要进行倒计时的最大值,单位是秒
     * @param interval
     * 倒计时的间隔，单位是秒
     */
    public CountDownHelper(final TextView textView,
                           final String defaultString, int max, int interval) {
        this.textView = textView;
        // 由于CountDownTimer并不是准确计时，在onTick方法调用的时候，time会有1-10ms左右的误差，这会导致最后一秒不会调用onTick()
        // 因此，设置间隔的时候，默认减去了10ms，从而减去误差。
        // 经过以上的微调，最后一秒的显示时间会由于10ms延迟的积累，导致显示时间比1s长max*10ms的时间，其他时间的显示正常,总时间正常
        countDownTimer = new CountDownTimer(max * 1000, interval * 1000 - 10) {
            @Override
            public void onTick(long time) {
                // 第一次调用会有1-10ms的误差，因此需要+15ms，防止第一个数不显示，第二个数显示2s
                textView.setText(((time + 15) / 1000)+ "");
                textView.setTextColor(Color.RED);


                Log.d("CountDownTextViewHelper", "time = " + (time) + " text = "
                        + ((time + 15) / 1000));
            }
            @Override
            public void onFinish() {
                textView.setEnabled(true);
                textView.setText(defaultString);
                if (listener != null) {
                    listener.finish();
                }
            }
        };
    }
    /**
     * 开始 倒计时
     */
    public void start() {
        textView.setEnabled(false);
        countDownTimer.start();
    }

    /**
     * 设置倒计时结束的监听器
     * @param listener
     */
    public void setOnFinishListener(OnFinishListener listener) {
        this.listener = listener;
    }

    /**
     * 倒计时结束的回调接口
     */
    public interface OnFinishListener {
         void finish();
    }
}
