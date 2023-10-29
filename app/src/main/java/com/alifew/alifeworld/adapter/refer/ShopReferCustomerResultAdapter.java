package com.alifew.alifeworld.adapter.refer;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.refer.ReferResultCustomerResponse;

import java.util.List;

public class ShopReferCustomerResultAdapter extends RecyclerView.Adapter<ShopReferCustomerResultAdapter.Viewholder> {

    List<ReferResultCustomerResponse.Customer> resultCustomerList;

    public ShopReferCustomerResultAdapter(List<ReferResultCustomerResponse.Customer> resultCustomerList) {
        this.resultCustomerList = resultCustomerList;
    }

    @NonNull
    @Override
    public ShopReferCustomerResultAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_refer_customer_result_card, parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ShopReferCustomerResultAdapter.Viewholder holder, int position) {

        ReferResultCustomerResponse.Customer response = resultCustomerList.get(position);
        holder.phoneText.setText(response.phone);
        holder.pointsText.setText(response.point);
        holder.positionText.setText(response.position);

        holder.giftNameText.setText(response.giftName);

        holder.statusButton.setImageDrawable(holder.itemView.getContext().getDrawable(
                response.status.equals("done") ? R.drawable.ic_done : R.drawable.ic_pending));
    }

    @Override
    public int getItemCount() {
        return resultCustomerList.size();
    }

    private OnItemDeleteListener onItemDeleteListener;
    private OnStatusButtonClick onStatusButtonClick;

    public interface OnStatusButtonClick {
        void OnStatusClick(int position);
    }

    public interface OnItemDeleteListener {
        void OnItemDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemDeleteListener onItemClickListener, OnStatusButtonClick onStatusButtonClick) {
        this.onItemDeleteListener = onItemClickListener;
        this.onStatusButtonClick = onStatusButtonClick;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView positionText, pointsText, phoneText, giftNameText;
        ImageView deleteButton, statusButton;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            positionText = itemView.findViewById(R.id.positionText);
            giftNameText = itemView.findViewById(R.id.giftNameText);
            pointsText = itemView.findViewById(R.id.pointsText);
            statusButton = itemView.findViewById(R.id.statusButton);
            phoneText = itemView.findViewById(R.id.phoneText);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(v -> {
                if (onItemDeleteListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemDeleteListener.OnItemDeleteClick(position);
                    }
                }
            });

            statusButton.setOnClickListener(v -> {
                if (onStatusButtonClick != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onStatusButtonClick.OnStatusClick(position);
                    }
                }
            });
        }
    }
}
