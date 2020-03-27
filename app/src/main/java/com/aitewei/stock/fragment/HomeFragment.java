package com.aitewei.stock.fragment;

import com.aitewei.stock.R;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
