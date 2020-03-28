package com.aitewei.stock.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.aitewei.stock.R;

import java.lang.ref.WeakReference;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity {

    private SplashHandler handler;

    private static class SplashHandler extends Handler {
        private WeakReference<SplashActivity> mActivity;

        SplashHandler(SplashActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SplashActivity activity = mActivity.get();
            if (activity != null) {
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        handler = new SplashHandler(this);
        handler.sendEmptyMessageDelayed(1, 2500);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeMessages(1);
        }
        super.onDestroy();
    }
}
