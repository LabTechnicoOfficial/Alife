package com.alifew.alife.EarningApp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.alifew.alife.EarningApp.Model.Notice.Notice_response;
import com.alifew.alife.R;

import java.util.List;

public class Notice_adapter extends RecyclerView.Adapter<Notice_adapter.AppViewholder> {
    List<Notice_response> noticeList;

    public Notice_adapter(List<Notice_response> noticeList) {
        this.noticeList = noticeList;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.earning_card_notice, parent, false);
        return new AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        Notice_response response = noticeList.get(position);

        holder.noticeTitle.setText(response.getHeadline());
        holder.notice.setText(response.getNoticebody());
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView noticeTitle, notice;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            noticeTitle = itemView.findViewById(R.id.noticeTitleID);
            notice = itemView.findViewById(R.id.noticeID);
        }
    }
}
