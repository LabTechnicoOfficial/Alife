package com.alifew.alifeworld.adapter.stock;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.DB.dao.ProductDao;
import com.alifew.alifeworld.DB.entity.Products;
import com.alifew.alifeworld.R;

import java.util.List;

public class ShopPrintProductStockAdapter extends RecyclerView.Adapter<ShopPrintProductStockAdapter.ViewHolder> {

    List<Products> productsList;
    ProductDao productDao;


    public ShopPrintProductStockAdapter(List<Products> productsList, ProductDao productDao) {
        this.productsList = productsList;
        this.productDao = productDao;
    }

    @NonNull
    @Override
    public ShopPrintProductStockAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_print_product_stock_card, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ShopPrintProductStockAdapter.ViewHolder holder, int position) {
        Products products = productsList.get(position);
        holder.titleText.setText(products.getName() + " " + products.getProductID());

        List<Products> typeList = productDao.getProductsTypes(products.getProductID());

        if (typeList.size() > 1) {
            holder.stockText.setVisibility(View.GONE);
            holder.stockAvailableText.setVisibility(View.GONE);
            holder.typeView.setVisibility(View.VISIBLE);

            ShopPrintProductTypeStockAdapter adapter = new ShopPrintProductTypeStockAdapter(typeList);
            holder.typeView.setAdapter(adapter);
        } else {
            holder.stockText.setVisibility(View.VISIBLE);
            holder.stockAvailableText.setVisibility(View.VISIBLE);
            holder.typeView.setVisibility(View.GONE);
            holder.stockText.setText("Stock: " + products.getStock());
            holder.stockAvailableText.setText("Av. Stock: " + products.getStockAvailable());
        }
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, stockText, stockAvailableText;
        RecyclerView typeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.titleText);
            stockText = itemView.findViewById(R.id.stockText);
            stockAvailableText = itemView.findViewById(R.id.stockAvailableText);
            typeView = itemView.findViewById(R.id.typeView);
            typeView.setHasFixedSize(true);
            typeView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }
}
