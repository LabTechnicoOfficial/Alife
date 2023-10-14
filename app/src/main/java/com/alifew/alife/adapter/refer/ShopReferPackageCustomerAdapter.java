package com.alifew.alife.adapter.refer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.model.refer.ReferPackageCustomerResponse;

import java.util.List;

public class ShopReferPackageCustomerAdapter extends RecyclerView.Adapter<ShopReferPackageCustomerAdapter.ViewHolder> {
    List<ReferPackageCustomerResponse> customerList;

    public ShopReferPackageCustomerAdapter(List<ReferPackageCustomerResponse> customerList) {
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public ShopReferPackageCustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_refer_package_customer_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopReferPackageCustomerAdapter.ViewHolder holder, int position) {

        ReferPackageCustomerResponse response = customerList.get(position);
        holder.positionText.setText(String.valueOf(position+1));
        holder.phoneText.setText(response.referPhone);
        holder.pointsText.setText(String.valueOf(response.referPoints));
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView positionText, phoneText, pointsText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            positionText = itemView.findViewById(R.id.positionText);
            phoneText = itemView.findViewById(R.id.phoneText);
            pointsText = itemView.findViewById(R.id.pointsText);

        }
    }
}
