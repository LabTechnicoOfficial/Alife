package com.alifew.alife.adapter.Barcode;

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

import com.alifew.alife.DB.ProductDao;
import com.alifew.alife.DB.Products;
import com.alifew.alife.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

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

//        try {
//            Picasso.get().load(response.getImage()).into(holder.productImage);
//        }catch (Exception e){
//
//        }

        holder.titleText.setText(response.getName());
        Glide.with(holder.itemView.getContext())
                .load(response.getImage())
                .centerCrop()
                .placeholder(R.drawable.loader)
                .into(holder.productImage);
/*

        if (response.getType().isEmpty()) {
            holder.typeText.setVisibility(View.GONE);
        } else {
            holder.typeText.setVisibility(View.VISIBLE);
            holder.typeText.setText(Html.fromHtml("Type: <b>" + response.getType() + "<b>"));
        }
*/

        holder.priceText.setText(Html.fromHtml("Price: <b>" + response.getPrice() + "<b> tk"));

        if (response.getPrintCheck().equals("1")) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    Toast.makeText(holder.itemView.getContext(), response.getProductID(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(holder.itemView.getContext(), response.getProductID(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


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

//    @Override
//    public void onCheckBoxClick(int position, boolean state) {
//        //Products response = typeList.get(position);
//
//        Log.d("dataxx", "onCheckBoxClick: "+String.valueOf(typeList.size()));
//    }

    public interface OnCheckBoxClickListener {
        void onCheckBoxClick(int position, boolean state);
    }

    public void setOnClickListener(OnCheckBoxClickListener onCheckBoxClickListener) {
        this.onCheckBoxClickListener = onCheckBoxClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView titleText, typeText, priceText;
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

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (onCheckBoxClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onCheckBoxClickListener.onCheckBoxClick(position, isChecked);
                        }
                    }
                }
            });

        }
    }
}
