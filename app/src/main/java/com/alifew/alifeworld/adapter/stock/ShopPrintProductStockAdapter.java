package com.alifew.alifeworld.adapter.stock;

import android.annotation.SuppressLint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.DB.dao.ProductDao;
import com.alifew.alifeworld.DB.entity.Products;
import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.Shop_profile_response;
import com.alifew.alifeworld.session.SessionManagement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShopPrintProductStockAdapter extends RecyclerView.Adapter<ShopPrintProductStockAdapter.ViewHolder> {

    List<Products> productsList;
    ProductDao productDao;
    Shop_profile_response shopProfileResponse;

    public ShopPrintProductStockAdapter(List<Products> productsList, ProductDao productDao, Shop_profile_response shopProfileResponse) {
        this.productsList = productsList;
        this.productDao = productDao;
        this.shopProfileResponse = shopProfileResponse;
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
        holder.titleText.setText(products.getName());

        List<Products> typeList = productDao.getProductsTypes(products.getProductID());

        if (position == 0) {
            holder.topLayout.setVisibility(View.VISIBLE);
            holder.shopNameText.setText(shopProfileResponse.getStore01e_name());
            holder.shopOwnerText.setText("Owner: " + shopProfileResponse.getStore01e_owner());
            holder.shopAddressText.setText("Address: " + shopProfileResponse.getStore01e_location());
        } else {
            holder.topLayout.setVisibility(View.GONE);
        }

        if (position == productsList.size() - 1) {
            holder.dateText.setVisibility(View.VISIBLE);
            holder.appNameText.setVisibility(View.VISIBLE);

            holder.appNameText.setText(Html.fromHtml(holder.itemView.getContext().getResources().getString(R.string.powered_by_text)));

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy hh:mm aa", Locale.getDefault());
            String currentDateTime = sdf.format(new Date());
            holder.dateText.setText(currentDateTime);
        } else {
            holder.dateText.setVisibility(View.GONE);
            holder.appNameText.setVisibility(View.GONE);
        }

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
        TextView titleText, stockText, stockAvailableText, shopNameText, dateText, appNameText, shopOwnerText, shopAddressText;
        RecyclerView typeView;
        ConstraintLayout topLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.titleText);
            stockText = itemView.findViewById(R.id.stockText);
            stockAvailableText = itemView.findViewById(R.id.stockAvailableText);
            typeView = itemView.findViewById(R.id.typeView);
            typeView.setHasFixedSize(true);
            typeView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

            topLayout = itemView.findViewById(R.id.topLayout);
            shopNameText = itemView.findViewById(R.id.shopNameText);
            dateText = itemView.findViewById(R.id.dateText);
            appNameText = itemView.findViewById(R.id.appNameText);
            shopOwnerText = itemView.findViewById(R.id.shopOwnerText);
            shopAddressText = itemView.findViewById(R.id.shopAddressText);

        }
    }
}
