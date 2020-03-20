package com.aitewei.stock.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aitewei.stock.R;

/**
 * 加载view
 */
public class LoadGroupView extends FrameLayout {
    public static final int TYPE_LOADING = 1;
    public static final int TYPE_ERROR = 2;
    public static final int TYPE_EMPTY = 3;

    private LinearLayout loadingView;
    private LinearLayout errorView;
    private LinearLayout emptyView;
    private TextView tvErrorMsg;

    public LoadGroupView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public LoadGroupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadGroupView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_load_view, null);
        loadingView = view.findViewById(R.id.loading_view);
        errorView = view.findViewById(R.id.error_view);
        emptyView = view.findViewById(R.id.empty_view);
        tvErrorMsg = view.findViewById(R.id.tv_msg);

        errorView.setVisibility(GONE);
        emptyView.setVisibility(GONE);
        addView(view);
    }

    private int loadType;

    public void setLoadType(int loadType) {
        this.loadType = loadType;
        loadingView.setVisibility(GONE);
        errorView.setVisibility(GONE);
        emptyView.setVisibility(GONE);
        if (loadType == TYPE_LOADING) {
            loadingView.setVisibility(VISIBLE);
        } else if (loadType == TYPE_ERROR) {
            errorView.setVisibility(VISIBLE);
        } else if (loadType == TYPE_EMPTY) {
            emptyView.setVisibility(VISIBLE);
        }
    }

    public int getLoadType() {
        return loadType;
    }

    public void setLoadError(String msg) {
        loadingView.setVisibility(GONE);
        emptyView.setVisibility(GONE);
        errorView.setVisibility(VISIBLE);
        tvErrorMsg.setText(msg + "");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
