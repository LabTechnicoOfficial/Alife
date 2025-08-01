package com.alifew.alifeworld.adapter.refer;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.alifew.alifeworld.R;
import com.alifew.alifeworld.adapter.Customer_package_adapter;
import com.alifew.alifeworld.model.refer.CustomerShopReferPackageResponse;

import java.util.List;

public class Customer_shop_refer_package_adapter extends RecyclerView.Adapter<Customer_shop_refer_package_adapter.Viewholder>{
    private final List<CustomerShopReferPackageResponse> packageList;
    public Customer_shop_refer_package_adapter(List<CustomerShopReferPackageResponse> packageList) {
        this.packageList = packageList;
    }

    @NonNull
    @Override
    public Customer_shop_refer_package_adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_shop_refer_package_card, parent, false);
        return new Customer_shop_refer_package_adapter.Viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Customer_shop_refer_package_adapter.Viewholder holder, int position) {
        CustomerShopReferPackageResponse response = packageList.get(position);
        holder.packageNameText.setText(response.title);
        holder.packageOwnerCountText.setText(response.customerCount.toString());
        holder.minPackagePointText.setText(response.minReferPackagePoint);
        holder.winnerText.setText(response.winnerAmount);
        holder.giftText.setText(response.gift);
        if (response.customerCount > 0) {
            holder.packageHistory.setVisibility(VISIBLE);
            holder.packageHistory.setText("প্যাকেজ কাস্টমার দেখুন");
        }else {
            holder.packageHistory.setVisibility(GONE);
        }
    }


    private Customer_shop_refer_package_adapter.onItemClickListener listener;

    public interface onItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnClickListener(Customer_shop_refer_package_adapter.onItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView packageNameText, minPackagePointText, packageOwnerCountText, winnerText, giftText, packageHistory;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            packageNameText = itemView.findViewById(R.id.packageNameTextID);
            minPackagePointText = itemView.findViewById(R.id.minPackagePointTextId);
            packageOwnerCountText = itemView.findViewById(R.id.packageOwnerAmountTextID);
            winnerText = itemView.findViewById(R.id.winnerTextID);
            giftText = itemView.findViewById(R.id.giftTextID);
            packageHistory = itemView.findViewById(R.id.packageHistoryId);
            packageHistory.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(position);
                    }
                }
            });
        }
    }
}
