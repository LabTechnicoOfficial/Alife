package com.alifew.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.model.systemetic_sell_details_response;

import java.util.List;

public class Systemetic_sell_details_adapter extends RecyclerView.Adapter<Systemetic_sell_details_adapter.AppViewholder> {
    private LayoutInflater layoutInflater;
    private List<systemetic_sell_details_response> sell_product_list;

    public Systemetic_sell_details_adapter(List<systemetic_sell_details_response> sell_product_list) {
        this.sell_product_list = sell_product_list;
    }

    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sell_customer_history_alert_card, parent, false);
        return new AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(AppViewholder holder, int position) {
        systemetic_sell_details_response product = sell_product_list.get(position);
        holder.product_name.setText(product.getProduct_name());
        holder.amount.setText(product.getProduct_amount());
        holder.type.setText(product.getType());
        holder.price.setText(product.getPrice());

    }

    @Override
    public int getItemCount() {
        return sell_product_list.size();
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView product_name, amount, type, price;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            product_name = (TextView) itemView.findViewById(R.id.productNameID);
            amount = (TextView) itemView.findViewById(R.id.amountID);
            type = (TextView) itemView.findViewById(R.id.typeID);
            price = (TextView) itemView.findViewById(R.id.priceID);
        }
    }

}
