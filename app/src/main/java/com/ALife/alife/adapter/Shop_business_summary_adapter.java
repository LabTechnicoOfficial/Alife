package com.ALife.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.model.get_shop_business_summary_response;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Shop_business_summary_adapter extends RecyclerView.Adapter<Shop_business_summary_adapter.AppViewholder> {
    List<get_shop_business_summary_response> summaryList;
    private ItemClickListener listener;

    public Shop_business_summary_adapter(List<get_shop_business_summary_response> summaryList) {
        this.summaryList = summaryList;
    }

    @NonNull
    @Override
    public Shop_business_summary_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_bussiness_summary_card, parent, false);
        return new Shop_business_summary_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Shop_business_summary_adapter.AppViewholder holder, int position) {
        get_shop_business_summary_response summaryResponse = summaryList.get(position);

        Picasso.get().load(summaryResponse.getImage()).into(holder.imageView);
        holder.descriptionText.setText(summaryResponse.getDescription());
        holder.creditInText.setText(summaryResponse.getCredit_in());
        holder.creditOutText.setText(summaryResponse.getCredit_out());
        holder.totalInvestText.setText(summaryResponse.getTotal_invest());
        holder.dateText.setText(summaryResponse.getDate());
        holder.timeText.setText(summaryResponse.getTime());
    }

    @Override
    public int getItemCount() {
        return summaryList.size();
    }

    public interface ItemClickListener {
        void ItemClick(int position);
    }

    public void setOnclickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        CircularImageView imageView;
        TextView descriptionText, creditInText, creditOutText, totalInvestText, dateText, timeText;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageID);
            descriptionText = itemView.findViewById(R.id.descriptionTextID);
            creditInText = itemView.findViewById(R.id.creditInID);
            creditOutText = itemView.findViewById(R.id.creditOutID);
            totalInvestText = itemView.findViewById(R.id.totalInvestTextID);
            dateText = itemView.findViewById(R.id.dateID);
            timeText = itemView.findViewById(R.id.timeID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.ItemClick(position);
                        }
                    }
                }
            });


        }
    }
}
