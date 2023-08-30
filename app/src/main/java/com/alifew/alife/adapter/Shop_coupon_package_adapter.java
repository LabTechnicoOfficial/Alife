package com.alifew.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.model.cupon.Package_response;

import java.util.List;

public class Shop_coupon_package_adapter extends RecyclerView.Adapter<Shop_coupon_package_adapter.AppViewHolder> {

    private List<Package_response> packagesList;
    private String cupon_available;

    public Shop_coupon_package_adapter(List<Package_response> packagesList, String cupon_available) {
        this.packagesList = packagesList;
        this.cupon_available = cupon_available;
    }

    @NonNull
    @Override
    public Shop_coupon_package_adapter.AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_coupon_packages_card, parent, false);
        return new Shop_coupon_package_adapter.AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Shop_coupon_package_adapter.AppViewHolder holder, int position) {
        Package_response response = packagesList.get(position);
        holder.packageNameText.setText(response.getPackage_name());
        holder.sellAmountText.setText(response.getPackageSellAmount());
        holder.packageOwnerAmountText.setText(response.getMaximum_package_owner());
        holder.winnerText.setText(response.getWinner());
        holder.giftText.setText(response.getGift());
        if (cupon_available.equals("1")) {
            holder.packageHistory.setText("প্যাকেজ কাস্টমার দেখুন");
        } else {
            holder.packageHistory.setText("প্যাকেজ হিস্ট্রি দেখুন");
        }
    }

    @Override
    public int getItemCount() {
        return packagesList.size();
    }

    private onItemClickListener listener;
    private onItemDeleteListener deleteListener;

    public interface onItemClickListener {
        void OnItemClick(int position);
    }

    public interface onItemDeleteListener {
        void OnItemDelete(int position);
    }

    public void setOnClickListener(Shop_coupon_package_adapter.onItemClickListener listener, Shop_coupon_package_adapter.onItemDeleteListener deleteListener) {
        this.listener = listener;
        this.deleteListener = deleteListener;
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        TextView packageNameText, sellAmountText, packageOwnerAmountText, winnerText, giftText, packageHistory;
        ImageView deleteButton;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);

            packageNameText = itemView.findViewById(R.id.packageNameTextID);
            sellAmountText = itemView.findViewById(R.id.sellAmountTextID);
            packageOwnerAmountText = itemView.findViewById(R.id.packageOwnerAmountTextID);
            deleteButton = itemView.findViewById(R.id.deleteButton);
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

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            deleteListener.OnItemDelete(position);
                        }
                    }
                }
            });
        }
    }
}
