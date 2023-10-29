package com.alifew.alifeworld.adapter.Barcode;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.DB.dao.ProductDao;
import com.alifew.alifeworld.DB.entity.Products;
import com.alifew.alifeworld.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Shop_product_barcode_print_adapter extends RecyclerView.Adapter<Shop_product_barcode_print_adapter.ViewHolder> {
    private List<Products> productList = new ArrayList<>();
    List<Products> typeList = new ArrayList<>();
    ProductDao productDao;

    public Shop_product_barcode_print_adapter(List<Products> productList, ProductDao productDao) {
        this.productList = productList;
        this.productDao = productDao;
    }

    @NonNull
    @Override
    public Shop_product_barcode_print_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_product_barcode_print_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Shop_product_barcode_print_adapter.ViewHolder holder, int position) {
        Products response = productList.get(position);


        holder.titleText.setText(response.getName());
        Glide.with(holder.itemView.getContext())
                .load(response.getImage())
                .centerCrop()
                .placeholder(R.drawable.loader)
                .into(holder.productImage);


        holder.priceText.setText(Html.fromHtml("Price: <b>" + response.getPrice() + "<b> tk"));

        holder.checkBox.setChecked(response.getPrintCheck().equals("1"));

        typeList = productDao.getProductsTypes(response.getProductID());

        if (typeList.size() > 1) {
            holder.typeLayout.setVisibility(View.VISIBLE);
            holder.checkBox.setVisibility(View.GONE);
            Shop_product_type_view_adapter typeAdapter = new Shop_product_type_view_adapter(typeList, productDao);
            // typeAdapter.setOnClickListener(Shop_product_barcode_print_adapter.this::onCheckBoxClick);
            holder.typeView.setAdapter(typeAdapter);

        } else {
            holder.typeLayout.setVisibility(View.GONE);
            holder.checkBox.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private OnCheckBoxClickListener onCheckBoxClickListener;
    private MarkAllClickListener markAllClickListener;

    public interface MarkAllClickListener {
        void onMarkAllClick(int position);
    }

    public interface OnCheckBoxClickListener {
        void onCheckBoxClick(int position, boolean state);
    }

    public void setOnClickListener(OnCheckBoxClickListener onCheckBoxClickListener, MarkAllClickListener markAllClickListener) {
        this.onCheckBoxClickListener = onCheckBoxClickListener;
        this.markAllClickListener = markAllClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView titleText, typeText, priceText, markAllButton;
        CheckBox checkBox;
        LinearLayout typeLayout;
        RecyclerView typeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            titleText = itemView.findViewById(R.id.titleText);
            typeText = itemView.findViewById(R.id.typeText);
            priceText = itemView.findViewById(R.id.priceText);
            checkBox = itemView.findViewById(R.id.checkBox);
            typeLayout = itemView.findViewById(R.id.typeLayout);
            typeView = itemView.findViewById(R.id.typeView);
            typeView.setHasFixedSize(true);
            typeView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            markAllButton = itemView.findViewById(R.id.markAllButton);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (buttonView.isPressed()) {
                        if (onCheckBoxClickListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                onCheckBoxClickListener.onCheckBoxClick(position, isChecked);
                            }
                        }
                    }

                }
            });

            markAllButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (markAllClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            markAllClickListener.onMarkAllClick(position);
                        }
                    }
                }
            });

        }
    }
}
