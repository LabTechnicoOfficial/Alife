package com.ALife.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.model.local_sell.get_local_sell_product_response;
import com.ALife.alife.view.Shop.Shop_local_sell_select_product_fragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Shop_local_sell_select_product_adapter extends RecyclerView.Adapter<Shop_local_sell_select_product_adapter.AppViewholder> {
    private List<get_local_sell_product_response> productList;
    private LayoutInflater layoutInflater;
private OnItemClickListener listener;
    public Shop_local_sell_select_product_adapter(List<get_local_sell_product_response> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.local_sell_select_product_card, parent, false);
        return new AppViewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        get_local_sell_product_response response = productList.get(position);
        holder.product_name.setText(response.getProduct_details());
        holder.product_price.setText(response.getPrice());
        if(!response.getImage().isEmpty())
        Picasso.get().load(response.getImage()).into(holder.product_image);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public interface OnItemClickListener {
        void itemClick(int position);
    }
    public void setOnClickListener(OnItemClickListener listener)
    {
        this.listener=listener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView product_name, product_price, product_unit, product_discount;
        ImageView product_image;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.productlabelID);
            product_image = itemView.findViewById(R.id.productImageID);
            product_price = (TextView) itemView.findViewById(R.id.priceID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.itemClick(position);
                        }
                    }
                }
            });
        }
    }
}
