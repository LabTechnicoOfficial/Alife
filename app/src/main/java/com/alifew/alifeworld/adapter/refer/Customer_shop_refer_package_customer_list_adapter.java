package com.alifew.alifeworld.adapter.refer;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.refer.CustomerReferPackageCustomer;

import java.util.List;

public class Customer_shop_refer_package_customer_list_adapter extends RecyclerView.Adapter<Customer_shop_refer_package_customer_list_adapter.ViewHolder> {
    private final List<CustomerReferPackageCustomer> customerList;

    public Customer_shop_refer_package_customer_list_adapter(List<CustomerReferPackageCustomer> customerList) {
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public Customer_shop_refer_package_customer_list_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_refer_package_customer_list_card, parent, false);
        return new Customer_shop_refer_package_customer_list_adapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Customer_shop_refer_package_customer_list_adapter.ViewHolder holder, int position) {
        CustomerReferPackageCustomer response = customerList.get(position);

        holder.positionText.setText(position + 1 +".");

        holder.phoneText.setText(response.customerName);

        holder.pointsText.setText(response.points.toString());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView phoneText, pointsText, positionText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            phoneText = itemView.findViewById(R.id.contactText);
            pointsText = itemView.findViewById(R.id.pointsText);
            positionText = itemView.findViewById(R.id.positionText);
        }
    }
}
