package com.aitewei.stock.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.widget.TextView;

import com.aitewei.stock.R;

import butterknife.BindView;

/**
 * 开户
 */
public class OpenAccountFragment extends BaseFragment {

    @BindView(R.id.btn_open_account)
    TextView btnOpenAccount;

    public static OpenAccountFragment newInstance() {
        OpenAccountFragment fragment = new OpenAccountFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_open_account;
    }

    @Override
    protected void initView() {
        btnOpenAccount.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        btnOpenAccount.setOnClickListener(v -> {
            //打开浏览器
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse("https://hhr.jhscco.com/?/#/openAccountPage?user_id=114492&source=share"));
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {

    }
}
