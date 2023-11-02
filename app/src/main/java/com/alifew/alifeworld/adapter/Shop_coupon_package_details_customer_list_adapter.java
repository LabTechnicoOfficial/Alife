package com.alifew.alifeworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.cupon.CustomerFor_cupon_response;

import java.util.List;

public class Shop_coupon_package_details_customer_list_adapter extends RecyclerView.Adapter<Shop_coupon_package_details_customer_list_adapter.AppViewHolder> {

    private List<CustomerFor_cupon_response> packageCustomerList;

    public Shop_coupon_package_details_customer_list_adapter(List<CustomerFor_cupon_response> packageCustomerList) {
        this.packageCustomerList = packageCustomerList;
    }

    @NonNull
    @Override
    public Shop_coupon_package_details_customer_list_adapter.AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_coupon_package_details_customer_list_card, parent, false);
        return new Shop_coupon_package_details_customer_list_adapter.AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Shop_coupon_package_details_customer_list_adapter.AppViewHolder holder, int position) {
        CustomerFor_cupon_response response = packageCustomerList.get(position);
        holder.phoneText.setText(response.getCustomer_phone());
        holder.sellAmountText.setText(response.getSell_amount());
        holder.positionText.setText(String.valueOf(position + 1));
        holder.pointsText.setText(response.points);
    }

    @Override
    public int getItemCount() {
        return packageCustomerList.size();
    }

    private OnAddIconClickListener onAddIconClickListener;

    public interface OnAddIconClickListener {
        public void onAddClick(int position);
    }

    public void setOnItemClickListener(OnAddIconClickListener onItemClickListener) {
        this.onAddIconClickListener = onItemClickListener;
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        TextView phoneText, sellAmountText, positionText, pointsText;
        ImageView addButton;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);

            phoneText = itemView.findViewById(R.id.phoneText);
            sellAmountText = itemView.findViewById(R.id.sellAmountTextID);
            positionText = itemView.findViewById(R.id.positionText);
            pointsText = itemView.findViewById(R.id.pointsText);
            addButton = itemView.findViewById(R.id.addButton);

            addButton.setOnClickListener(v -> {
                if (onAddIconClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onAddIconClickListener.onAddClick(position);
                    }
                }
            });
        }
    }
}
