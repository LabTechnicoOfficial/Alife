package com.alifew.alifeworld.adapter.stock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.DB.entity.Products;
import com.alifew.alifeworld.R;

import java.util.List;

public class ShopPrintProductTypeStockAdapter extends RecyclerView.Adapter<ShopPrintProductTypeStockAdapter.ViewHolder> {

    List<Products> typeList;

    public ShopPrintProductTypeStockAdapter(List<Products> typeList) {
        this.typeList = typeList;
    }

    @NonNull
    @Override
    public ShopPrintProductTypeStockAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_print_product_type_stock_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopPrintProductTypeStockAdapter.ViewHolder holder, int position) {

        Products products = typeList.get(position);
        holder.typeText.setText("Type: "+products.getType());
        holder.stockText.setText("Stock: "+products.getStock());
        holder.stockAvailableText.setText("Av. stock: "+products.getStockAvailable());
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView typeText, stockText, stockAvailableText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            typeText = itemView.findViewById(R.id.typeText);
            stockText = itemView.findViewById(R.id.stockText);
            stockAvailableText = itemView.findViewById(R.id.stockAvailableText);
        }
    }
}
