package com.alifew.alife.adapter.refer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.model.refer.ReferResultCustomerResponse;

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

    @Override
    public void onBindViewHolder(@NonNull ShopReferCustomerResultAdapter.Viewholder holder, int position) {

        ReferResultCustomerResponse.Customer response = resultCustomerList.get(position);
        holder.phoneText.setText(response.phone);
        holder.pointsText.setText(response.point);
        holder.positionText.setText(response.position);
        holder.statusText.setText(response.status);
        holder.giftNameText.setText(response.giftName);
    }

    @Override
    public int getItemCount() {
        return resultCustomerList.size();
    }

    private OnItemDeleteListener onItemDeleteListener;

    public interface OnItemDeleteListener {
        void OnItemDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemDeleteListener onItemClickListener) {
        this.onItemDeleteListener = onItemClickListener;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView positionText, pointsText, statusText, phoneText, giftNameText;
        ImageView deleteButton;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            positionText = itemView.findViewById(R.id.positionText);
            giftNameText = itemView.findViewById(R.id.giftNameText);
            pointsText = itemView.findViewById(R.id.pointsText);
            statusText = itemView.findViewById(R.id.statusText);
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
        }
    }
}
