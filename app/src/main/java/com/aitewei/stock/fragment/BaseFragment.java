package com.aitewei.stock.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private Unbinder unBinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unBinder = ButterKnife.bind(this, view);

        initView();
        initData();
        return view;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onDestroyView() {
        if (unBinder != null) {
            unBinder.unbind();
        }
        super.onDestroyView();
    }
}
