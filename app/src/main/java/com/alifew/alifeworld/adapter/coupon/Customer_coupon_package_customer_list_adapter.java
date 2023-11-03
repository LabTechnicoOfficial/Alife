package com.alifew.alifeworld.adapter.coupon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.cupon.CustomerFor_cupon_response;

import java.util.List;

public class Customer_coupon_package_customer_list_adapter extends RecyclerView.Adapter<Customer_coupon_package_customer_list_adapter.AppViewHolder> {
    //ishtiak
    String phone;
    private List<CustomerFor_cupon_response> customerList;

    public Customer_coupon_package_customer_list_adapter(String phone, List<CustomerFor_cupon_response> customerList) {
        this.phone = phone;
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public Customer_coupon_package_customer_list_adapter.AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_coupon_package_customer_list_card, parent, false);
        return new Customer_coupon_package_customer_list_adapter.AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Customer_coupon_package_customer_list_adapter.AppViewHolder holder, int position) {
        CustomerFor_cupon_response response = customerList.get(position);

        holder.positionText.setText(String.valueOf(position + 1)+".");

        if (response.getCustomer_phone().equals(phone)) {
            holder.phoneText.setText("myself");
        } else {
//            holder.phoneText.setText(response.getCustomer_phone());
            holder.phoneText.setVisibility(View.INVISIBLE);
        }

        holder.sellAmountText.setText(response.getSell_amount());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        TextView phoneText, sellAmountText, positionText;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);

            phoneText = itemView.findViewById(R.id.contactText);
            sellAmountText = itemView.findViewById(R.id.sellAmountTextID);
            positionText = itemView.findViewById(R.id.positionText);
        }
    }
}
