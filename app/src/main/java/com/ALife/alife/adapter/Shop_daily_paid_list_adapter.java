package com.ALife.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.model.get_daily_sell_cash_response;

import java.util.List;

public class Shop_daily_paid_list_adapter extends RecyclerView.Adapter<Shop_daily_paid_list_adapter.AppViewholder>{
    List<get_daily_sell_cash_response> paid_list;

    public Shop_daily_paid_list_adapter(List<get_daily_sell_cash_response> paid_list) {
        this.paid_list = paid_list;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_daily_paid_list_card, parent, false);
        return new Shop_daily_paid_list_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        get_daily_sell_cash_response sell=paid_list.get(position);
        holder.customerName.setText(sell.getCustomer_name());
        holder.paidMethod.setText(sell.getPayment_system());
        holder.paidPayment.setText(sell.getPayment_amount());

    }

    @Override
    public int getItemCount() {
        return paid_list.size();
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView customerName, paidPayment, paidMethod;
        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            customerName = (TextView) itemView.findViewById(R.id.customerNameID);
            paidPayment = (TextView) itemView.findViewById(R.id.paidAmountID);
            paidMethod = (TextView) itemView.findViewById(R.id.paidMethodID);
        }
    }
}
