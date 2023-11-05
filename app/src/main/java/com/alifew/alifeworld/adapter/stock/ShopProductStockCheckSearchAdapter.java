package com.alifew.alifeworld.adapter.stock;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.DB.dao.ProductDao;
import com.alifew.alifeworld.DB.entity.Products;
import com.alifew.alifeworld.R;
import com.alifew.alifeworld.adapter.Barcode.Shop_product_type_view_adapter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ShopProductStockCheckSearchAdapter extends RecyclerView.Adapter<ShopProductStockCheckSearchAdapter.ViewHolder> {

    private List<Products> productList;
    List<Products> typeList = new ArrayList<>();
    ProductDao productDao;

    public ShopProductStockCheckSearchAdapter(List<Products> productList, ProductDao productDao) {
        this.productList = productList;
        this.productDao = productDao;
    }

    @NonNull
    @Override
    public ShopProductStockCheckSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_checkout_search_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopProductStockCheckSearchAdapter.ViewHolder holder, int position) {
        Products response = productList.get(position);


        holder.titleText.setText(response.getName()+ " "+response.getProductID());
        Glide.with(holder.itemView.getContext())
                .load(response.getImage())
                .centerCrop()
                .placeholder(R.drawable.loader)
                .into(holder.productImage);


        holder.priceText.setText(Html.fromHtml("Price: <b>" + response.getPrice() + "<b> tk"));


        typeList = productDao.getProductsTypes(response.getProductID());

        if (typeList.size() > 1) {
            holder.typeLayout.setVisibility(View.VISIBLE);
            Shop_product_type_view_adapter typeAdapter = new Shop_product_type_view_adapter(typeList, productDao);
            // typeAdapter.setOnClickListener(Shop_product_barcode_print_adapter.this::onCheckBoxClick);
            holder.typeView.setAdapter(typeAdapter);

        } else {
            holder.typeLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView titleText, typeText, priceText, markAllButton;

        LinearLayout typeLayout;
        RecyclerView typeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            titleText = itemView.findViewById(R.id.titleText);
            typeText = itemView.findViewById(R.id.typeText);
            priceText = itemView.findViewById(R.id.priceText);
            typeLayout = itemView.findViewById(R.id.typeLayout);
            typeView = itemView.findViewById(R.id.typeView);
            typeView.setHasFixedSize(true);
            typeView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }
}
