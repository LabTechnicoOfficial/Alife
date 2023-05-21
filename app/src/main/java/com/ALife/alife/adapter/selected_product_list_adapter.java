package com.ALife.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.Custom_Type.productSell_temp;
import com.ALife.alife.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class selected_product_list_adapter extends RecyclerView.Adapter<selected_product_list_adapter.AppViewholder> {
    private List<productSell_temp> productList;

    public selected_product_list_adapter(List<productSell_temp> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.selected_product_list_final_card, parent, false);
        return new selected_product_list_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
     productSell_temp product=productList.get(position);
        holder.productName.setText(product.getProduct_name());
        holder.amountText.setText(product.getAmount());
        holder.priceText.setText(product.getPrice());
        holder.typeText.setText(product.getType_name());
        Picasso.get().load(product.getProduct_image()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView productName, typeText, amountText, priceText;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.productImageID);
            productName = itemView.findViewById(R.id.productNameID);
            typeText = itemView.findViewById(R.id.typeID);
            amountText = itemView.findViewById(R.id.amountID);
            priceText = itemView.findViewById(R.id.priceID);
        }
    }
}
