package com.ALife.alife.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.API.ApiUtilize;
import com.ALife.alife.R;
import com.ALife.alife.adapter.Customer.Customer_allShop_adapter;
import com.ALife.alife.model.cupon.active_cupon;
import com.ALife.alife.model.cupon.cuponShop_response;
import com.ALife.alife.model.cupon.cupon_api;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Customer_coupon_shop_list_adapter extends RecyclerView.Adapter<Customer_coupon_shop_list_adapter.AppViewholder> {

    private List<cuponShop_response> shopList;

    public Customer_coupon_shop_list_adapter(List<cuponShop_response> shopList) {

        this.shopList = shopList;

    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_coupon_shop_list_card, parent, false);
        return new Customer_coupon_shop_list_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {

        cuponShop_response response = shopList.get(position);
        Picasso.get().load(response.getShop_image()).into(holder.shopImage);
        holder.shopNameText.setText(response.getShop_name());
        holder.phoneText.setText(response.getShop_phone());
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    private Customer_coupon_shop_list_adapter.OnItemClickListener mListener;
    public void setOnClickListener(Customer_coupon_shop_list_adapter.OnItemClickListener listener) {
        mListener = listener;
    }
    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        ImageView shopImage;
        TextView shopNameText, phoneText;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            shopImage = itemView.findViewById(R.id.shopImageID);
            shopNameText = itemView.findViewById(R.id.shopNameTextID);
            phoneText = itemView.findViewById(R.id.phoneTextID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
