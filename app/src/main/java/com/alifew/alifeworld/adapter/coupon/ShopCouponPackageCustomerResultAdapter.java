package com.alifew.alifeworld.adapter.coupon;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.Utils.Constants;
import com.alifew.alifeworld.model.cupon.ShopCouponCustomerResponse;

import java.util.List;

public class ShopCouponPackageCustomerResultAdapter extends RecyclerView.Adapter<ShopCouponPackageCustomerResultAdapter.ViewHolder> {
    List<ShopCouponCustomerResponse.Customer> customerList;

    public ShopCouponPackageCustomerResultAdapter(List<ShopCouponCustomerResponse.Customer> customerList) {
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public ShopCouponPackageCustomerResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_coupon_package_customer_result_card, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ShopCouponPackageCustomerResultAdapter.ViewHolder holder, int position) {
        ShopCouponCustomerResponse.Customer response = customerList.get(position);
        holder.phoneText.setText(response.phone);
        holder.pointsText.setText(response.point);
        holder.positionText.setText(response.position);
        holder.amountText.setText(response.amount + Constants.TAKA_SYMBOL);
        holder.giftNameText.setText(response.giftMenu);

        holder.statusButton.setImageDrawable(holder.itemView.getContext().getDrawable(
                response.status.equals("done") ? R.drawable.ic_done : R.drawable.ic_pending));
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    private OnStatusChangeListener onStatusChangeListener;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnStatusChangeListener {
        public void onStatusClick(int position);
    }


    public interface OnDeleteClickListener {
        public void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnStatusChangeListener onStatusChangeListener, OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
        this.onStatusChangeListener = onStatusChangeListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView positionText, pointsText, phoneText, giftNameText, amountText;
        ImageView deleteButton, statusButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            positionText = itemView.findViewById(R.id.positionText);
            giftNameText = itemView.findViewById(R.id.giftNameText);
            pointsText = itemView.findViewById(R.id.pointsText);
            statusButton = itemView.findViewById(R.id.statusButton);
            phoneText = itemView.findViewById(R.id.phoneText);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            amountText = itemView.findViewById(R.id.amountText);

            statusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onStatusChangeListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onStatusChangeListener.onStatusClick(position);
                        }
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDeleteClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onDeleteClickListener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
