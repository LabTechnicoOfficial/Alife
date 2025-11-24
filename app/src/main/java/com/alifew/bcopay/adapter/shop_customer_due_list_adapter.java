package com.alifew.bcopay.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.R;
import com.alifew.bcopay.model.get_shop_customer_due_list_response;

import java.text.DecimalFormat;
import java.util.List;

public class shop_customer_due_list_adapter extends RecyclerView.Adapter<shop_customer_due_list_adapter.AppViewholder> {
    List<get_shop_customer_due_list_response> transactionList;
    OnDueClickListener listener;

    public shop_customer_due_list_adapter(List<get_shop_customer_due_list_response> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_customer_due_list_card, parent, false);
        return new shop_customer_due_list_adapter.AppViewholder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        get_shop_customer_due_list_response transcation = transactionList.get(position);

        Double totalPrice = Double.parseDouble(transcation.getSell_price());
        holder.totalPriceText.setTextColor(R.color.black);
        holder.totalPriceText.setText(new DecimalFormat("##.##").format(totalPrice));

        Double duePrice = Double.parseDouble(transcation.getDue());
        if (duePrice >= 0.0) {
            // holder.dueText.setTextColor(000000);
            holder.dueText.setTextColor(R.color.black);
            holder.dueText.setText(new DecimalFormat("##.##").format(duePrice));
        } else if (duePrice < 0.0) {
            duePrice = duePrice * (-1);
            holder.dueText.setTextColor(0xffff0000);
            holder.dueText.setText(new DecimalFormat("##.##").format(duePrice));
        }


        Double paidPrice = Double.parseDouble(transcation.getPay());
        holder.paidPriceText.setTextColor(R.color.black);
        holder.paidPriceText.setText(new DecimalFormat("##.##").format(paidPrice));
        holder.dateText.setTextColor(R.color.black);
        holder.dateText.setText(transcation.getDate());
        holder.timeText.setTextColor(R.color.black);
        holder.timeText.setText(transcation.time);

        Double totalDuePrice = Double.parseDouble(transcation.getTotal_due());
        if (totalDuePrice >= 0.0) {
            // holder.dueText.setTextColor(000000);
            holder.totalDueText.setTextColor(R.color.black);
            holder.totalDueText.setText(new DecimalFormat("##.##").format(totalDuePrice));
        } else if (totalDuePrice < 0.0) {
            totalDuePrice = totalDuePrice * (-1);
            holder.totalDueText.setTextColor(0xffff0000);
            holder.totalDueText.setText(new DecimalFormat("##.##").format(totalDuePrice));
        }
        //holder.totalDueText.setText(new DecimalFormat("##.##").format(totalDuePrice));

    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public interface OnDueClickListener {
        void OnDueLick(int position);
    }

    public void SetOnClickListener(OnDueClickListener listener) {
        this.listener = listener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView dueText, dateText, timeText, totalPriceText, paidPriceText, totalDueText;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            dueText = (TextView) itemView.findViewById(R.id.dueTextID);
            dateText = (TextView) itemView.findViewById(R.id.dateID);
            totalPriceText = (TextView) itemView.findViewById(R.id.totalPriceID);
            paidPriceText = (TextView) itemView.findViewById(R.id.paidPriceID);
            totalDueText = (TextView) itemView.findViewById(R.id.totalDueTextID);
            timeText = itemView.findViewById(R.id.timeID);
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
