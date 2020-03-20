package com.aitewei.stock.activity;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import com.aitewei.stock.R;
import com.aitewei.stock.databinding.ActivityCompanyStockDetailBinding;
import com.aitewei.stock.entity.CompanyStockListEntity;

/**
 * 公司股票详情
 */
public class CompanyStockDetailActivity extends BaseActivity {

    public static void start(Context context, String companyNameCode, CompanyStockListEntity.DataTableBean entity) {
        Intent starter = new Intent(context, CompanyStockDetailActivity.class);
        starter.putExtra("companyNameCode", companyNameCode);
        starter.putExtra("entity", entity);
        context.startActivity(starter);
    }

    private ActivityCompanyStockDetailBinding binding;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_stock_detail;
    }

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            String companyNameCode = intent.getStringExtra("companyNameCode");
            binding.tvTitle.setText(companyNameCode + "");

            CompanyStockListEntity.DataTableBean entity = (CompanyStockListEntity.DataTableBean)
                    intent.getSerializableExtra("entity");
            if (entity != null) {
                binding.setEntity(entity);
            }
        }
    }

    @Override
    protected boolean isUseButterKnife() {
        return false;
    }

}
