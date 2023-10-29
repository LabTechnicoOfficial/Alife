package com.alifew.alifeworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.cupon.Package_response;

import java.util.List;

public class Customer_package_adapter extends RecyclerView.Adapter<Customer_package_adapter.AppViewHolder> {
    private List<Package_response> packagesList;
    private String cupon_available;


    public Customer_package_adapter(List<Package_response> packagesList, String cupon_available) {
        this.packagesList = packagesList;
        this.cupon_available = cupon_available;

    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_coupon_package_card, parent, false);
        return new Customer_package_adapter.AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        Package_response response = packagesList.get(position);
        holder.packageNameText.setText(response.getPackage_name());
        holder.packageOwnerAmountText.setText(response.getMaximum_package_owner());
        holder.sellAmountText.setText(response.getPackageSellAmount());
        holder.winnerText.setText(response.getWinner());
        holder.giftText.setText(response.getGift());
        if (cupon_available.equals("1")) {
            holder.packageHistory.setText("প্যাকেজ কাস্টমার দেখুন");
        } else {
            holder.packageHistory.setText("প্যাকেজ হিস্ট্রি দেখুন");
        }

        if (response.getInPackage()) {
            holder.inPackageIcon.setVisibility(View.VISIBLE);
        } else {
            holder.inPackageIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return packagesList.size();
    }

    private Customer_package_adapter.onItemClickListener listener;

    public interface onItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnClickListener(Customer_package_adapter.onItemClickListener listener) {
        this.listener = listener;
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        TextView packageNameText, sellAmountText, packageOwnerAmountText, winnerText, giftText, packageHistory;
        ImageView inPackageIcon;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            inPackageIcon = itemView.findViewById(R.id.inPackageIcon);
            packageNameText = itemView.findViewById(R.id.packageNameTextID);
            sellAmountText = itemView.findViewById(R.id.sellAmountTextID);
            packageOwnerAmountText = itemView.findViewById(R.id.packageOwnerAmountTextID);
            winnerText = itemView.findViewById(R.id.winnerTextID);
            giftText = itemView.findViewById(R.id.giftTextID);
            packageHistory = itemView.findViewById(R.id.packageHistoryId);
            packageHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
