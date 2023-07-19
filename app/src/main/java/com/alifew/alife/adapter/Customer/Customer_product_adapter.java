package com.alifew.alife.adapter.Customer;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.model.Get_product_response;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Customer_product_adapter extends RecyclerView.Adapter<Customer_product_adapter.AppViewholder>{
    List<Get_product_response> productList;

    public Customer_product_adapter(List<Get_product_response> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public Customer_product_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_product_card, parent, false);
        return new Customer_product_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Customer_product_adapter.AppViewholder holder, int position) {
        Get_product_response response = productList.get(position);

        Picasso.get().load(response.getProduct_image()).into(holder.productImage);
        holder.productLabel.setText(response.getProduct_name());
        holder.priceText.setText(response.getSelling_price());
        holder.amountText.setText(response.getStock_amount());
        String discount = response.getProduct_offer();
        Double product_discount=Double.parseDouble(discount);
        Double selling_price = Double.parseDouble(response.getSelling_price());
        double price_with_offer= selling_price-selling_price*(product_discount/100);

        if(product_discount==0.0){
            holder.discountLayout.setVisibility(View.INVISIBLE);
        }else {
            holder.discountLayout.setVisibility(View.VISIBLE);
            holder.discountText.setText(String.valueOf(price_with_offer));
            holder.priceText.setPaintFlags(holder.priceText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productLabel, priceText, discountText, amountText;
        LinearLayout discountLayout;
        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            discountLayout = itemView.findViewById(R.id.discountLayoutID);
            productLabel = itemView.findViewById(R.id.productlabelID);
            priceText = itemView.findViewById(R.id.priceID);
            amountText = itemView.findViewById(R.id.amountID);
            discountText = itemView.findViewById(R.id.discountID);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}
