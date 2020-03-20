package com.aitewei.stock.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aitewei.stock.entity.CompanyListEntity;
import com.aitewei.stock.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Description:    公司列表
 * Author:         张俊杰
 * CreateDate:     2020/3/15 下午7:41
 * Version:        1.0
 */
public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.ItemViewHolder> {
    private Context context;
    private List<CompanyListEntity.DataTableBean> list;

    public CompanyListAdapter(Context context, List<CompanyListEntity.DataTableBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<CompanyListEntity.DataTableBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CompanyListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_company_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        CompanyListEntity.DataTableBean bean = list.get(position);
        holder.tvName.setText(bean.get公司名称() + "");
        holder.tvCode.setText(bean.get公司代码() + "");
        String abridge = bean.get公司缩写();
        if (TextUtils.isEmpty(abridge)) {
            abridge = "--";
        }
        holder.tvAbridge.setText(abridge + "");
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public final class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvCode;
        TextView tvAbridge;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvCode = itemView.findViewById(R.id.tv_code);
            tvAbridge = itemView.findViewById(R.id.tv_abridge);

            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    if (list != null) {
                        CompanyListEntity.DataTableBean bean = list.get(getLayoutPosition());
                        itemClickListener.onItemClick(bean);
                    }
                }
            });
        }
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(CompanyListEntity.DataTableBean bean);
    }

}
