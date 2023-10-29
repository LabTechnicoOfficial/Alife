package com.alifew.alifeworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.DB.entity.LocalSellProducts;
import com.alifew.alifeworld.R;
import com.alifew.alifeworld.Utils.ImageHelper;

import java.util.List;

public class Shop_local_sell_select_product_adapter extends RecyclerView.Adapter<Shop_local_sell_select_product_adapter.AppViewholder> {
    private List<LocalSellProducts> productList;
    private LayoutInflater layoutInflater;
    private OnItemClickListener listener;

    public Shop_local_sell_select_product_adapter(List<LocalSellProducts> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new AppViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.local_sell_select_product_card, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        LocalSellProducts response = productList.get(position);
        holder.product_name.setText(response.getName());
        holder.product_price.setText(holder.itemView.getContext().getResources().getString(R.string.sell_price) + ": " +response.getSellPrice());
        holder.buyPriceText.setText(holder.itemView.getContext().getResources().getString(R.string.buy_price) + ": " + response.getBuyPrice());

        ImageHelper.imageLoader(holder.itemView.getContext(), holder.product_image, response.getImage());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public interface OnItemClickListener {
        void itemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView product_name, product_price, buyPriceText, product_discount;
        ImageView product_image;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.productlabelID);
            product_image = itemView.findViewById(R.id.productImage);
            product_price = (TextView) itemView.findViewById(R.id.priceID);
            buyPriceText = itemView.findViewById(R.id.buyPriceText);
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
