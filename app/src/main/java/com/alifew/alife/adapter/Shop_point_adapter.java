package com.alifew.alife.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.model.points.Shop_local_sell_point_response;

import java.util.List;

public class Shop_point_adapter extends RecyclerView.Adapter<Shop_point_adapter.Viewholder> {
    List<Shop_local_sell_point_response> shopSellPointRulesList;

    public Shop_point_adapter(List<Shop_local_sell_point_response> shopSellPointRulesList) {
        this.shopSellPointRulesList = shopSellPointRulesList;
    }

    @NonNull
    @Override
    public Shop_point_adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_point_adapter, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Shop_point_adapter.Viewholder holder, int position) {
        Shop_local_sell_point_response response = shopSellPointRulesList.get(position);
        holder.pointsCriteriaText.setText(response.amount
                + " " + holder.itemView.getContext().getResources().getString(R.string.point_text1)
                + " " + response.points
                + " " + holder.itemView.getContext().getResources().getString(R.string.point_text2));
    }

    @Override
    public int getItemCount() {
        return shopSellPointRulesList.size();
    }

    private OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void OnDeleteClick(int position);
    }

    public void setOnClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView pointsCriteriaText;
        ImageView deleteButton;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            pointsCriteriaText = itemView.findViewById(R.id.pointsCriteriaText);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDeleteClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onDeleteClickListener.OnDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
