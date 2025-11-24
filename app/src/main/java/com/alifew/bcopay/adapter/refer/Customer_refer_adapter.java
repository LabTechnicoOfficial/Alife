package com.alifew.bcopay.adapter.refer;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.R;
import com.alifew.bcopay.model.refer.CustomerShopReferResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Customer_refer_adapter extends RecyclerView.Adapter<Customer_refer_adapter.Viewholder>{
    private List<CustomerShopReferResponse> referList;

    public Customer_refer_adapter(List<CustomerShopReferResponse> referList) {
        this.referList = referList;
    }

    @NonNull
    @Override
    public Customer_refer_adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Customer_refer_adapter.Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_refer_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Customer_refer_adapter.Viewholder holder, int position) {
        CustomerShopReferResponse response = referList.get(position);
        holder.nameText.setText(response.name);
        holder.createdTimeText.setText(response.startAt);
        holder.endTimeText.setText(response.endAt);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = (String) android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date());

        String targetdate = response.endAt + " 23:59:59";


        try {

            Date date1 = myFormat.parse(currentTime);
            Date date2 = myFormat.parse(targetdate);
            if (!(date1.getTime() > date2.getTime())) {
                long time_difference = date2.getTime() - date1.getTime();
                long days_difference = (time_difference / (1000 * 60 * 60 * 24)) % 365;
                long years_difference = (time_difference / (1000l * 60 * 60 * 24 * 365));
                long minutes_difference = (time_difference / (1000 * 60)) % 60;
                long hours_difference = (time_difference / (1000 * 60 * 60)) % 24;
                String duration = days_difference + " দিন " + hours_difference + " ঘণ্টা " + minutes_difference + " মিনিট ";
                holder.durationText.setText(duration);
            } else {
                holder.durationText.setText("সময় শেষ");

            }

        }
        // Catch parse exception
        catch (ParseException excep) {
            excep.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return referList.size();
    }

    private Customer_refer_adapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(Customer_refer_adapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView nameText, createdTimeText, endTimeText, durationText;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.shopNameText);
            durationText = itemView.findViewById(R.id.durationText);
            createdTimeText = itemView.findViewById(R.id.createdTimeText);
            endTimeText = itemView.findViewById(R.id.endTimeText);

            itemView.setOnClickListener(v-> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }
}
