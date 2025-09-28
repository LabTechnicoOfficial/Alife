package com.alifew.bcopay.adapter.coupon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.R;
import com.alifew.bcopay.model.cupon.cupon_response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Shop_coupon_adapter extends RecyclerView.Adapter<Shop_coupon_adapter.AppViewholder> {
    private List<cupon_response> couponList;

    public Shop_coupon_adapter(List<cupon_response> couponList) {
        this.couponList = couponList;
    }

    @NonNull
    @Override
    public Shop_coupon_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_coupon_card, parent, false);
        return new Shop_coupon_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Shop_coupon_adapter.AppViewholder holder, int position) {

        cupon_response response = couponList.get(position);
        holder.couponNameText.setText(response.getCupon_name());
        //holder.durationText.setText(response.getTime_range());
        holder.createdTimeText.setText(response.getCreation_date());
        holder.endTimeText.setText(response.getEnd_date());
        Date startDate, endDate;
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = (String) android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date());

        String targetdate = response.getEnd_date() + " 23:59:59";


        //SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        try {
            // Use parse method to get date object of both dates

            Date date1 = myFormat.parse(currentTime);
            Date date2 = myFormat.parse(targetdate);
            if (!(date1.getTime() > date2.getTime())) {
                // Calucalte time difference in milliseconds
                long time_difference = date2.getTime() - date1.getTime();
                // Calucalte time difference in days
                long days_difference = (time_difference / (1000 * 60 * 60 * 24)) % 365;
                // Calucalte time difference in years
                long years_difference = (time_difference / (1000l * 60 * 60 * 24 * 365));
                // Calucalte time difference in seconds

                // Calucalte time difference in minutes
                long minutes_difference = (time_difference / (1000 * 60)) % 60;

                // Calucalte time difference in hours
                long hours_difference = (time_difference / (1000 * 60 * 60)) % 24;
                // Show difference in years, in days, hours, minutes, and seconds
                String duration = days_difference + " দিন " + hours_difference + " ঘণ্টা " + minutes_difference + " মিনিট ";
                holder.durationText.setText(duration);
            } else {
                holder.durationText.setText("সময় শেষ");
                holder.notifyButton.setVisibility(View.GONE);
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

    private onItemClickListener listener;
    private onItemDeleteListener deleteListener;
    private onItemNotifyListener notifyListener;

    public interface onItemClickListener {
        void OnItemClick(int position);
    }

    public interface onItemDeleteListener {
        void OnItemDelete(int position);
    }

    public interface onItemNotifyListener {
        void OnItemNotify(int position);
    }

    public void setOnClickListener(onItemClickListener listener, onItemDeleteListener deleteListener, onItemNotifyListener notifyListener) {
        this.listener = listener;
        this.deleteListener = deleteListener;
        this.notifyListener = notifyListener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView couponNameText, durationText, createdTimeText, endTimeText, notifyButton;
        ImageView deleteButton;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            deleteButton = itemView.findViewById(R.id.deleteButton);
            couponNameText = itemView.findViewById(R.id.couponNameTextID);
            durationText = itemView.findViewById(R.id.durationTextID);
            createdTimeText = itemView.findViewById(R.id.createdTimeTextID);
            endTimeText = itemView.findViewById(R.id.endTimeTextID);
            notifyButton = itemView.findViewById(R.id.notifyButtonID);

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

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            deleteListener.OnItemDelete(position);
                        }
                    }
                }
            });

            notifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (notifyListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            notifyListener.OnItemNotify(position);
                        }
                    }
                }
            });
        }
    }

}
