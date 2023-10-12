package com.alifew.alife.adapter.refer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.model.refer.ReferResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ShopReferAdapter extends RecyclerView.Adapter<ShopReferAdapter.Viewholder> {

    private List<ReferResponse> referList;

    public ShopReferAdapter(List<ReferResponse> referList) {
        this.referList = referList;
    }

    @NonNull
    @Override
    public ShopReferAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_refer_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopReferAdapter.Viewholder holder, int position) {

        ReferResponse response = referList.get(position);
        holder.nameText.setText("");
        holder.createdTimeText.setText(response.startAt);
        holder.endTimeText.setText(response.endAt);

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = (String) android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date());

        String targetdate = response.endAt + " 23:59:59";


        try {

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

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView nameText, createdTimeText, endTimeText, durationText;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            durationText = itemView.findViewById(R.id.durationText);
            createdTimeText = itemView.findViewById(R.id.createdTimeText);
            endTimeText = itemView.findViewById(R.id.endTimeText);
        }
    }
}
