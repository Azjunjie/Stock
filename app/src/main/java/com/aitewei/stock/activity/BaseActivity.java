package com.aitewei.stock.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:    Activity基类
 * Author:         张俊杰
 * CreateDate:     2020/3/15 下午8:28
 * Version:        1.0
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isUseButterKnife()) {
            setContentView(getLayoutId());
            unbinder = ButterKnife.bind(this);
        }
        initView();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected boolean isUseButterKnife() {
        return true;
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }
}
