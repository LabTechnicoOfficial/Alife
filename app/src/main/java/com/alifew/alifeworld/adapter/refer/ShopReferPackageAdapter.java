package com.alifew.alifeworld.adapter.refer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.refer.ReferPackageResponse;

import java.util.List;

public class ShopReferPackageAdapter extends RecyclerView.Adapter<ShopReferPackageAdapter.ViewHolder> {
    private List<ReferPackageResponse> referPackageList;

    public ShopReferPackageAdapter(List<ReferPackageResponse> referPackageList) {
        this.referPackageList = referPackageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_refer_package_card, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReferPackageResponse response = referPackageList.get(position);
        holder.packageNameText.setText(response.title);
        holder.sellAmountText.setText(response.minAmount);
        holder.packageOwnerAmountText.setText(String.valueOf(response.userCount));
        holder.winnerText.setText(response.winnerAmount);
        holder.giftText.setText(response.gift);
      /*  if (cupon_available.equals("1")) {
            holder.packageHistory.setText("প্যাকেজ কাস্টমার দেখুন");
        } else {
            holder.packageHistory.setText("প্যাকেজ হিস্ট্রি দেখুন");
        }*/
    }

    @Override
    public int getItemCount() {
        return referPackageList.size();
    }

    private OnItemClickListener onItemClickListener;
    private OnItemDeleteClickListener onItemDeleteClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemDeleteClickListener {
        void onItemDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener, OnItemDeleteClickListener onItemDeleteClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.onItemDeleteClickListener = onItemDeleteClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView packageNameText, sellAmountText, packageOwnerAmountText, winnerText, giftText, packageHistory;
        ImageView deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            packageNameText = itemView.findViewById(R.id.packageNameTextID);
            sellAmountText = itemView.findViewById(R.id.sellAmountTextID);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            winnerText = itemView.findViewById(R.id.winnerTextID);
            giftText = itemView.findViewById(R.id.giftTextID);
            packageHistory = itemView.findViewById(R.id.packageHistoryId);
            packageOwnerAmountText = itemView.findViewById(R.id.packageOwnerAmountTextID);

            deleteButton.setOnClickListener(v -> {
                if (onItemDeleteClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemDeleteClickListener.onItemDeleteClick(position);
                    }
                }
            });


            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }
}
