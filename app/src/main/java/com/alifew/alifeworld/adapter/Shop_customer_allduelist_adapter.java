package com.alifew.alifeworld.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.get_shop_all_due_details_response;

import java.text.DecimalFormat;
import java.util.List;

public class Shop_customer_allduelist_adapter extends RecyclerView.Adapter<Shop_customer_allduelist_adapter.AppViewholder> {
    List<get_shop_all_due_details_response> dueList;
    private OnItemClickListener listener;

    public Shop_customer_allduelist_adapter(List<get_shop_all_due_details_response> dueList) {
        this.dueList = dueList;
    }

    @NonNull
    @Override
    public Shop_customer_allduelist_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.allduelist_card, parent, false);
        return new Shop_customer_allduelist_adapter.AppViewholder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Shop_customer_allduelist_adapter.AppViewholder holder, int position) {
        get_shop_all_due_details_response due = dueList.get(position);
        holder.customerName.setTextColor(R.color.black);
        holder.customerName.setText(due.getCustomer_name());
        holder.customerPhone.setTextColor(R.color.black);
        holder.customerPhone.setText(due.getCustomer_phone());
        Double duePrice = Double.parseDouble(due.getDue_amount());
        //holder.dueAmountText.setText(new DecimalFormat("##.##").format(duePrice));
        if(duePrice>=0.0)
        {
            // holder.dueText.setTextColor(000000);
            holder.dueAmountText.setTextColor(R.color.black);
            holder.dueAmountText.setText(new DecimalFormat("##.##").format(duePrice));
        }else if(duePrice<0.0)
        {
            duePrice=duePrice*(-1);
            holder.dueAmountText.setTextColor(0xffff0000);
            holder.dueAmountText.setText(new DecimalFormat("##.##").format(duePrice));
        }

      //  holder.dueAmountText.setText(due.getDue_amount());
        holder.dateText.setTextColor(R.color.black);
        holder.dateText.setText(due.getDate());
       // holder.totalDueText.setText(due.getTotal_due());
        Double totalDuePrice=Double.parseDouble(due.getTotal_due());
        if(totalDuePrice>=0.0)
        {
            // holder.dueText.setTextColor(000000);
            holder.totalDueText.setTextColor(R.color.black);
            holder.totalDueText.setText(new DecimalFormat("##.##").format(totalDuePrice));
        }else if(totalDuePrice<0.0)
        {
            totalDuePrice=totalDuePrice*(-1);
            holder.totalDueText.setTextColor(0xffff0000);
            holder.totalDueText.setText(new DecimalFormat("##.##").format(totalDuePrice));
        }
        holder.payAmounText.setTextColor(R.color.black);
        holder.payAmounText.setText(due.getPay_amount());
        holder.sellingPrice.setTextColor(R.color.black);
        holder.sellingPrice.setText(due.getSell_price());
    }

    @Override
    public int getItemCount() {
        return dueList.size();
    }

    public interface OnItemClickListener {
        void OnDueLick(int position);
    }

    public void SetOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView dateText, customerName,customerPhone, dueAmountText, totalDueText, payAmounText, sellingPrice;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateID);
            customerName = itemView.findViewById(R.id.customerNameID);
            customerPhone = itemView.findViewById(R.id.customerPhoneID);
            sellingPrice = itemView.findViewById(R.id.sellingPriceID);
            payAmounText = itemView.findViewById(R.id.payTextID);
            dueAmountText = itemView.findViewById(R.id.dueID);
            totalDueText = itemView.findViewById(R.id.totalDueTextID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnDueLick(position);
                        }
                    }
                }
            });
        }
    }
}
