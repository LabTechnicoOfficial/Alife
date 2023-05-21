package com.ALife.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.model.get_local_business_subtitle_response;
import com.ALife.alife.model.get_local_business_title_response;
import com.ALife.alife.model.shop_local_page_item_list_response;

import java.util.List;

public class Shop_local_page_details_adapter extends RecyclerView.Adapter<Shop_local_page_details_adapter.AppViewholder> {
    private List<shop_local_page_item_list_response> subtitleList;
    private OnItemClickListener listener;

    public Shop_local_page_details_adapter(List<shop_local_page_item_list_response> subtitleList) {
        this.subtitleList = subtitleList;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_local_page_details_card, parent, false);
        return new Shop_local_page_details_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        shop_local_page_item_list_response response = subtitleList.get(position);
        holder.nameText.setText(response.getName());
        holder.amountText.setText(response.getAmount());
        holder.priceText.setText(response.getPrice());
        holder.totalPriceText.setText(response.getTotal_price());
        holder.titleText.setText(response.getTitle());

    }

    @Override
    public int getItemCount() {
        return subtitleList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView nameText, amountText, priceText, totalPriceText, titleText;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            nameText = (TextView) itemView.findViewById(R.id.nameTextID);
            amountText = (TextView) itemView.findViewById(R.id.amountTextID);
            priceText = (TextView) itemView.findViewById(R.id.priceTextID);
            totalPriceText = (TextView) itemView.findViewById(R.id.totalPriceTextID);
            titleText = (TextView) itemView.findViewById(R.id.titleTextID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        //getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });


        }
    }
}
