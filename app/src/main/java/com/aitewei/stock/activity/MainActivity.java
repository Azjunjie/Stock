package com.aitewei.stock.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aitewei.stock.R;
import com.aitewei.stock.fragment.HomeFragment;
import com.aitewei.stock.fragment.OpenAccountFragment;
import com.aitewei.stock.fragment.OptimalIndexFragment;
import com.aitewei.stock.fragment.TrendStructureFragment;
import com.aitewei.stock.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.parent_view)
    FrameLayout parentView;
    @BindView(R.id.btn_home)
    LinearLayout btnHome;
    @BindView(R.id.btn_optimal_index)
    LinearLayout btnOptimalIndex;
    @BindView(R.id.btn_trend_structure)
    LinearLayout btnTrendStructure;
    @BindView(R.id.btn_open_account)
    LinearLayout btnOpenAccount;

    private FragmentManager fragmentManager;

    private HomeFragment homeFragment;
    private OptimalIndexFragment indexFragment;
    private TrendStructureFragment structureFragment;
    private OpenAccountFragment accountFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        btnHome.setSelected(true);
    }

    @Override
    protected void initData() {
        fragmentManager = getSupportFragmentManager();
        showFragment(0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_PHONE_STATE}, 1000);
            }
        }
    }

    @OnClick({R.id.btn_qr_code, R.id.btn_home, R.id.btn_optimal_index, R.id.btn_trend_structure, R.id.btn_open_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_qr_code://二维码
                showQrCodePopup();
                break;
            case R.id.btn_home://首页
                showFragment(0);
                break;
            case R.id.btn_optimal_index://最优指标
                showFragment(1);
                break;
            case R.id.btn_trend_structure://走势结构
                showFragment(2);
                break;
            case R.id.btn_open_account://开户
                showFragment(3);
                break;
        }
    }

    private void showFragment(int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);

        btnHome.setSelected(false);
        btnOptimalIndex.setSelected(false);
        btnTrendStructure.setSelected(false);
        btnOpenAccount.setSelected(false);
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance();
                    transaction.add(R.id.fragment_container, homeFragment, HomeFragment.class.getSimpleName());
                } else {
                    transaction.show(homeFragment);
                }
                btnHome.setSelected(true);
                break;
            case 1:
                if (indexFragment == null) {
                    indexFragment = OptimalIndexFragment.newInstance();
                    transaction.add(R.id.fragment_container, indexFragment, OptimalIndexFragment.class.getSimpleName());
                } else {
                    transaction.show(indexFragment);
                }
                btnOptimalIndex.setSelected(true);
                break;
            case 2:
                if (structureFragment == null) {
                    structureFragment = TrendStructureFragment.newInstance();
                    transaction.add(R.id.fragment_container, structureFragment, TrendStructureFragment.class.getSimpleName());
                } else {
                    transaction.show(structureFragment);
                }
                btnTrendStructure.setSelected(true);
                break;
            case 3:
                if (accountFragment == null) {
                    accountFragment = OpenAccountFragment.newInstance();
                    transaction.add(R.id.fragment_container, accountFragment, OpenAccountFragment.class.getSimpleName());
                } else {
                    transaction.show(accountFragment);
                }
                btnOpenAccount.setSelected(true);
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (homeFragment != null)
            transaction.hide(homeFragment);
        if (indexFragment != null)
            transaction.hide(indexFragment);
        if (structureFragment != null)
            transaction.hide(structureFragment);
        if (accountFragment != null)
            transaction.hide(accountFragment);
    }

    private PopupWindow qrCodePopup;

    private void showQrCodePopup() {
        if (qrCodePopup == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.popup_qr_code, null);
            qrCodePopup = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            qrCodePopup.setOnDismissListener(() -> ScreenUtils.setBackgroundAlpha(this, 1f));
            qrCodePopup.setFocusable(true);
            qrCodePopup.setTouchable(true);
            qrCodePopup.setOutsideTouchable(true);
        }
        ScreenUtils.setBackgroundAlpha(this, 0.5f);
        qrCodePopup.showAtLocation(parentView, Gravity.CENTER, 0, 0);
    }

}
