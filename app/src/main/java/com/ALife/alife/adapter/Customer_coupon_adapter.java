package com.ALife.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.model.cupon.cupon_response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Customer_coupon_adapter extends RecyclerView.Adapter<Customer_coupon_adapter.AppViewHolder> {
    private List<cupon_response> couponList;

    public Customer_coupon_adapter(List<cupon_response> couponList) {
        this.couponList = couponList;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_coupon_card, parent, false);
        return new Customer_coupon_adapter.AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        cupon_response response = couponList.get(position);
        holder.couponNameText.setText(response.getCupon_name());
       // holder.durationText.setText(response.getTime_range());
        holder.createdTimeText.setText(response.getCreation_date());
        holder.endTimeText.setText(response.getEnd_date());
        Date startDate, endDate;
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = (String) android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date());

        String targetdate=response.getEnd_date()+" 23:59:59";


        //SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        try {
            // Use parse method to get date object of both dates

            Date date1 = myFormat.parse(currentTime);
            Date date2 = myFormat.parse(targetdate);
            if(!(date1.getTime()>date2.getTime())){
                // Calucalte time difference in milliseconds
                long time_difference = date2.getTime() - date1.getTime();
                // Calucalte time difference in days
                long days_difference = (time_difference / (1000*60*60*24)) % 365;
                // Calucalte time difference in years
                long years_difference = (time_difference / (1000l*60*60*24*365));
                // Calucalte time difference in seconds

                // Calucalte time difference in minutes
                long minutes_difference = (time_difference / (1000*60)) % 60;

                // Calucalte time difference in hours
                long hours_difference = (time_difference / (1000*60*60)) % 24;
                // Show difference in years, in days, hours, minutes, and seconds
                String duration=days_difference+" দিন "+hours_difference+ " ঘণ্টা "+ minutes_difference+" মিনিট ";
                holder.durationText.setText(duration);}
            else
            {
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
        return couponList.size();
    }

    private Customer_coupon_adapter.onItemClickListener listener;

    public interface onItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnClickListener(Customer_coupon_adapter.onItemClickListener listener) {
        this.listener = listener;
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
//ishtiak 01
        TextView couponNameText, durationText, createdTimeText, endTimeText;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);

            couponNameText = itemView.findViewById(R.id.couponNameTextID);
            durationText = itemView.findViewById(R.id.durationTextID);
            createdTimeText = itemView.findViewById(R.id.createdTimeTextID);
            endTimeText = itemView.findViewById(R.id.endTimeTextID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
