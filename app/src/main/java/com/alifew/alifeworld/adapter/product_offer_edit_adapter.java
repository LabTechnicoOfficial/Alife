package com.alifew.alifeworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.get_product_offer_response;

import java.text.DecimalFormat;
import java.util.List;

public class product_offer_edit_adapter extends RecyclerView.Adapter<product_offer_edit_adapter.AppViewholder> {
    List<get_product_offer_response> offerList;
    String sell_price;
    private LayoutInflater layoutInflater;
    private OnItemOfferEditListener listener1;
    private OnItemOfferDeleteListener listener2;

    public product_offer_edit_adapter(List<get_product_offer_response> offerList, String sell_price) {
        this.offerList = offerList;
        this.sell_price = sell_price;
    }

    public interface OnItemOfferEditListener {
        void OnItemOfferEdit(int position);
    }

    public interface OnItemOfferDeleteListener {
        void OnItemOfferDelete(int position);
    }

    public void setOnClickListener(OnItemOfferEditListener listener1, OnItemOfferDeleteListener listener2) {
        this.listener1 = listener1;
        this.listener2 = listener2;
    }

    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.edit_offer_card, parent, false);
        return new AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        get_product_offer_response offer = offerList.get(position);
        holder.amount.setText(offer.getAmount());

        String percentage = offer.getPrice();
        holder.percentage.setText(percentage);
        Double total_price = Double.parseDouble(offer.getAmount()) * Double.parseDouble(sell_price);
        Double offer_price = total_price - (total_price * (Double.parseDouble(percentage) / 100));
        // holder.price.setText(product_type.getPrice());
        holder.price.setText(String.valueOf(new DecimalFormat("##.##").format(offer_price)));

    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView amount, percentage, price;
        ImageView edit, delete;


        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.packageTextID);
            percentage = itemView.findViewById(R.id.percentageTextID);
            price = (TextView) itemView.findViewById(R.id.priceID);
            edit = (ImageView) itemView.findViewById(R.id.editButton);
            delete = (ImageView) itemView.findViewById(R.id.deleteButton);
           edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener1 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener1.OnItemOfferEdit(position);
                        }
                    }
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener2 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                           listener2.OnItemOfferDelete(position);
                        }
                    }
                }
            });

        }
    }
}



