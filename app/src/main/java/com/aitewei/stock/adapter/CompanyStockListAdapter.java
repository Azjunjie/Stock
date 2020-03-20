package com.aitewei.stock.adapter;

import android.content.Context;

import com.aitewei.stock.R;
import com.aitewei.stock.entity.CompanyStockListEntity;

import java.util.List;

/**
 * Description:    公司列表
 * Author:         张俊杰
 * CreateDate:     2020/3/15 下午7:41
 * Version:        1.0
 */
public class CompanyStockListAdapter extends AbsBaseListViewAdapter<CompanyStockListEntity.DataTableBean> {

    public CompanyStockListAdapter(Context context, List<CompanyStockListEntity.DataTableBean> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void setData(ViewHolder viewHolder, int position) {
        CompanyStockListEntity.DataTableBean bean = list.get(position);
        viewHolder.setViewText(R.id.tv_zhpm, bean.get综合排名() + "");
        viewHolder.setViewText(R.id.tv_sylpm, bean.get收益率排名() + "");
        viewHolder.setViewText(R.id.tv_cglpm, bean.get成功率排名() + "");
        viewHolder.setViewText(R.id.tv_cgcb, bean.get持股持币() + "");
        viewHolder.setViewText(R.id.tv_code, bean.get公司代码() + "");
        viewHolder.setViewText(R.id.tv_gsdm, bean.get公式代码() + "");
        viewHolder.setViewText(R.id.tv_gszq, bean.get公式周期() + "");
        viewHolder.setViewText(R.id.tv_zycs, bean.get最优参数() + "");
        viewHolder.setViewText(R.id.tv_jylx, bean.get交易类型() + "");
    }

}
