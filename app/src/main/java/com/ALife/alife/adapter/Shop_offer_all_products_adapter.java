package com.ALife.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.model.get_shop_all_product_offer_response;

import java.util.List;

public class Shop_offer_all_products_adapter extends RecyclerView.Adapter<Shop_offer_all_products_adapter.AppViewholder> {

    private List<get_shop_all_product_offer_response> offerList;
    private OnItemDeletrListener listener;

    public Shop_offer_all_products_adapter(List<get_shop_all_product_offer_response> offerList) {
        this.offerList = offerList;
    }

    @NonNull
    @Override
    public Shop_offer_all_products_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_offer_all_products_card, parent, false);
        return new Shop_offer_all_products_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Shop_offer_all_products_adapter.AppViewholder holder, int position) {
        get_shop_all_product_offer_response response = offerList.get(position);
        holder.minAmountText.setText(response.getMinimum_amount());
        holder.minPriceText.setText(response.getMinimum_price());
        holder.offerText.setText(response.getOffer_percentage() + "%");
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public interface OnItemDeletrListener {
        void OnItemDelete(int position);
    }

    public void setOnItemClickListener(OnItemDeletrListener listener) {
        this.listener = listener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView minAmountText, minPriceText, offerText;
        ImageView deleteButton;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            minAmountText = itemView.findViewById(R.id.minProductAmountID);
            minPriceText = itemView.findViewById(R.id.minPriceID);
            offerText = itemView.findViewById(R.id.offerID);
            deleteButton = itemView.findViewById(R.id.deleteButtonID);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemDelete(position);
                        }
                    }
                }
            });
        }
    }
}
