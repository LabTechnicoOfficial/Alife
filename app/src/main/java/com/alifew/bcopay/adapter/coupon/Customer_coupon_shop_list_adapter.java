package com.alifew.bcopay.adapter.coupon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.R;
import com.alifew.bcopay.Utils.ImageHelper;
import com.alifew.bcopay.model.cupon.ShopResponse;

import java.util.List;

public class Customer_coupon_shop_list_adapter extends RecyclerView.Adapter<Customer_coupon_shop_list_adapter.AppViewholder> {

    private List<ShopResponse> shopList;

    public Customer_coupon_shop_list_adapter(List<ShopResponse> shopList) {

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

        ShopResponse response = shopList.get(position);

        ImageHelper.imageLoader(holder.itemView.getContext(),  holder.shopImage, response.getShop_image());
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
            shopNameText = itemView.findViewById(R.id.shopNameText);
            phoneText = itemView.findViewById(R.id.contactText);

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
