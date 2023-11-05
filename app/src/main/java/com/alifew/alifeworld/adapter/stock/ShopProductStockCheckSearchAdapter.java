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
import com.alifew.alifeworld.adapter.Sub_shop_adapter;
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


        holder.titleText.setText(response.getName());
        Glide.with(holder.itemView.getContext())
                .load(response.getImage())
                .centerCrop()
                .placeholder(R.drawable.loader)
                .into(holder.productImage);


        holder.priceText.setText(Html.fromHtml("Price: <b>" + response.getPrice() + "<b> tk"));


        typeList = productDao.getProductsTypes(response.getProductID());


        if (typeList.size() > 1) {
            holder.typeText.setVisibility(View.VISIBLE);
            holder.typeText.setText("Types: ");
            for (int i = 0; i < typeList.size(); i++) {
                holder.typeText.append(typeList.get(i).getName());

                if (i != typeList.size() - 1) {
                    holder.typeText.append(", ");
                }
            }
        } else {
            holder.typeText.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView titleText, typeText, priceText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            titleText = itemView.findViewById(R.id.titleText);
            typeText = itemView.findViewById(R.id.typeText);
            priceText = itemView.findViewById(R.id.priceText);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });

        }
    }
}
