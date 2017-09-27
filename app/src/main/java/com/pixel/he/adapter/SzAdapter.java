package com.pixel.he.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pixel.he.R;
import com.pixel.he.bean.SzBean;

import java.util.List;

import pixel.database.library.SqlTemplate;

/**
 * Created by pixel on 2017/9/26.
 */

public class SzAdapter extends RecyclerView.Adapter<SzAdapter.SzViewHolder> {

    private List<SzBean> beanList;
    private Activity activity;
    private String mDateType = "year";

    public SzAdapter(Activity activity, List<SzBean> beanList, String mDateType) {
        this.activity = activity;
        this.beanList = beanList;
        this.mDateType = mDateType;
    }

    @Override
    public SzAdapter.SzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SzAdapter.SzViewHolder(activity.getLayoutInflater().inflate(R.layout.sz_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SzAdapter.SzViewHolder holder, final int position) {
        final SzBean bean = beanList.get(position);

//        if ("year".equalsIgnoreCase(mDateType)) {
//            holder.textDate.setText(bean.year);
//        } else if ("month".equalsIgnoreCase(mDateType)) {
//            holder.textDate.setText(bean.month);
//        } else {
//            holder.textDate.setText(bean.day);
//        }
        holder.textDate.setText(bean.year + "-" + bean.month + "-" + bean.day + " " + bean.hour + ":" + bean.minutes);

        holder.textType.setText(bean.type);
        holder.textAmount.setText(bean.amount + " 元");
        holder.textNote.setText(bean.note);
        holder.textDescribe.setText(bean.describe);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(activity).setItems(new String[]{"删除"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SqlTemplate.delete(SzBean.class, bean._id, "_id");
                        beanList.remove(bean);
                        notifyDataSetChanged();
                    }
                }).create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public static class SzViewHolder extends RecyclerView.ViewHolder {
        TextView textDate;
        TextView textType;
        TextView textAmount;
        TextView textNote;
        TextView textDescribe;

        public SzViewHolder(View itemView) {
            super(itemView);

            textDate = (TextView) itemView.findViewById(R.id.textDate);
            textType = (TextView) itemView.findViewById(R.id.textType);
            textAmount = (TextView) itemView.findViewById(R.id.textAmount);
            textNote = (TextView) itemView.findViewById(R.id.textNote);
            textDescribe = (TextView) itemView.findViewById(R.id.textDescribe);
        }
    }
}
