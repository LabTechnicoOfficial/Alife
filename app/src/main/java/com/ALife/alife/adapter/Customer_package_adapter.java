package com.ALife.alife.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.API.ApiUtilize;
import com.ALife.alife.R;
import com.ALife.alife.model.cupon.active_cupon;
import com.ALife.alife.model.cupon.cupon_api;
import com.ALife.alife.model.cupon.package_response;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Customer_package_adapter extends RecyclerView.Adapter<Customer_package_adapter.AppViewHolder> {
    private List<package_response> packagesList;


    private String cupon_available;
    private int activeCupon;


    public Customer_package_adapter(List<package_response> packagesList, String cupon_available, int activeCupon) {
        this.packagesList = packagesList;
        this.cupon_available = cupon_available;
        this.activeCupon = activeCupon;
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
        String active = "";
        Log.d("msg16", String.valueOf(activeCupon));
        if (activeCupon == position) {
            active = "(active)";
        }
        package_response response = packagesList.get(position);
        holder.packageNameText.setText(response.getPackage_name());
        holder.packageOwnerAmountText.setText(response.getMaximum_package_owner());
        holder.sellAmountText.setText(response.getPackageSellAmount());
        holder.winnerText.setText(response.getWinner());
        holder.giftText.setText(response.getGift());
        if (cupon_available.equals("1")) {
            holder.packageHistory.setText("প্যাকেজ কাস্টমার দেখুন" + active);
        } else {
            holder.packageHistory.setText("প্যাকেজ হিস্ট্রি দেখুন" + active);
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

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);

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
