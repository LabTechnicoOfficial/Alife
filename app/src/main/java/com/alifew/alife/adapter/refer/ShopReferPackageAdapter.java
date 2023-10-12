package com.alifew.alife.adapter.refer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.model.cupon.Package_response;
import com.alifew.alife.model.refer.ReferPackageResponse;

import java.util.List;

public class ShopReferPackageAdapter extends RecyclerView.Adapter<ShopReferPackageAdapter.Viewholder>{
    private List<ReferPackageResponse> referPackageList;

    public ShopReferPackageAdapter(List<ReferPackageResponse> referPackageList) {
        this.referPackageList = referPackageList;
    }

    @NonNull
    @Override
    public ShopReferPackageAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_refer_package_card, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ShopReferPackageAdapter.Viewholder holder, int position) {
        ReferPackageResponse response = referPackageList.get(position);
        holder.packageNameText.setText(response.title);
        holder.sellAmountText.setText(response.minAmount);
        holder.packageOwnerAmountText.setText(response.userCount);
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

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView packageNameText, sellAmountText, packageOwnerAmountText, winnerText, giftText, packageHistory;
        ImageView deleteButton;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            packageNameText = itemView.findViewById(R.id.packageNameTextID);
            sellAmountText = itemView.findViewById(R.id.sellAmountTextID);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            winnerText = itemView.findViewById(R.id.winnerTextID);
            giftText = itemView.findViewById(R.id.giftTextID);
            packageHistory = itemView.findViewById(R.id.packageHistoryId);
        }
    }
}
