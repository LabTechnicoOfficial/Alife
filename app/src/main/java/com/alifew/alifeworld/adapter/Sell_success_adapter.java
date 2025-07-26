package com.alifew.alifeworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.Custom_Type.ProductSell;
import com.alifew.alifeworld.R;

import java.util.List;

public class Sell_success_adapter extends RecyclerView.Adapter<Sell_success_adapter.AppViewholder> {
    private LayoutInflater layoutInflater;
    private List<ProductSell> productList;

    public Sell_success_adapter(List<ProductSell> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public Sell_success_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.success_product_list_card, parent, false);
        return new Sell_success_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Sell_success_adapter.AppViewholder holder, int position) {
        ProductSell product = productList.get(position);
        holder.product_name.setText(product.getProduct_name());
        holder.product_amount.setText(product.getAmount());
        holder.product_price.setText(product.getPrice());


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class AppViewholder extends RecyclerView.ViewHolder {

        TextView product_name, product_amount, product_price;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            product_name = (TextView) itemView.findViewById(R.id.productNameID);
            product_amount = (TextView) itemView.findViewById(R.id.productAmountID);
            product_price = (TextView) itemView.findViewById(R.id.productPriceID);


        }
    }

}
