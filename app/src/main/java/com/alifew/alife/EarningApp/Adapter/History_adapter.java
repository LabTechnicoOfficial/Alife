package com.alifew.alife.EarningApp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.EarningApp.Model.Transaction.transactionHistory_response;
import com.alifew.alife.R;

import java.util.List;

public class History_adapter extends RecyclerView.Adapter<History_adapter.AppViewholder> {
    private List<transactionHistory_response> historyList;

    public History_adapter(List<transactionHistory_response> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.earning_card_history, parent, false);
        return new AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        transactionHistory_response response = historyList.get(position);

        holder.historyText.setText(response.getMessage());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView historyText;
        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            historyText = itemView.findViewById(R.id.historyTextID);
        }
    }
}
