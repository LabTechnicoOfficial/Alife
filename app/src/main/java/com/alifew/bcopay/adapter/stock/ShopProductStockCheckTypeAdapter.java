package com.alifew.bcopay.adapter.stock;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.DB.entity.Products;
import com.alifew.bcopay.R;
import com.alifew.bcopay.view.Shop.ShopProductStockCheckFragment;

import java.util.List;

public class ShopProductStockCheckTypeAdapter extends RecyclerView.Adapter<ShopProductStockCheckTypeAdapter.ViewHolder> {

    List<Products> typeList;
    ShopProductStockCheckFragment shopProductStockCheckFragment;

    public ShopProductStockCheckTypeAdapter(List<Products> typeList, ShopProductStockCheckFragment shopProductStockCheckFragment) {
        this.typeList = typeList;
        this.shopProductStockCheckFragment = shopProductStockCheckFragment;
    }

    @NonNull
    @Override
    public ShopProductStockCheckTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_product_stock_check_type_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopProductStockCheckTypeAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Products products = typeList.get(position);
        holder.stockText.setText(products.getStock());
        holder.typeText.setText(products.getType());
        holder.availableItemEditText.setText(products.getStockAvailable());

        holder.availableItemEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                shopProductStockCheckFragment.updateTypeItemValue(position, s.toString().trim());
            }
        });

    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView typeText, stockText;
        EditText availableItemEditText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeText = itemView.findViewById(R.id.typeText);
            stockText = itemView.findViewById(R.id.stockTypeText);
            availableItemEditText = itemView.findViewById(R.id.availableItemEditText);
        }
    }
}
