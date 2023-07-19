package com.alifew.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.model.cupon.customerFor_cupon_response;

import java.util.List;

public class Shop_coupon_package_details_customer_list_adapter extends RecyclerView.Adapter<Shop_coupon_package_details_customer_list_adapter.AppViewHolder> {

    private List<customerFor_cupon_response> packageCustomerList;

    public Shop_coupon_package_details_customer_list_adapter(List<customerFor_cupon_response> packageCustomerList) {
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
        customerFor_cupon_response response = packageCustomerList.get(position);
        holder.phoneText.setText(response.getCustomer_phone());
        holder.sellAmountText.setText(response.getSell_amount());
    }

    @Override
    public int getItemCount() {
        return packageCustomerList.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        TextView phoneText, sellAmountText;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);

            phoneText = itemView.findViewById(R.id.phoneTextID);
            sellAmountText = itemView.findViewById(R.id.sellAmountTextID);
        }
    }
}
