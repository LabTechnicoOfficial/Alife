package com.alifew.alifeworld.adapter.localsell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.local_sell.local_sell_history_response;

import java.util.List;

public class Shop_local_sell_history_adapter extends RecyclerView.Adapter<Shop_local_sell_history_adapter.AppViewHolder> {

    private List<local_sell_history_response> local_sell_list;

    public Shop_local_sell_history_adapter(List<local_sell_history_response> local_sell_list) {
        this.local_sell_list = local_sell_list;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_local_sell_history_card, parent, false);
        return new Shop_local_sell_history_adapter.AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {

        local_sell_history_response response = local_sell_list.get(position);
        holder.dateText.setText(response.getDate());
        holder.customerContactText.setText(response.getCustomer_phone());
        if (response.getCustomer_name().isEmpty()) {
            holder.nameText.setText(response.getCustomer_phone());
        } else {
            holder.nameText.setText(response.getCustomer_name());
        }

        holder.sellPriceText.setText(response.getSell_price());
        holder.buyPriceText.setText(response.getBuy_price());
        holder.profitText.setText(response.getProfit());
        holder.pointsText.setText(response.getPoints().isEmpty() ? "0.0" : response.getPoints());
        holder.referNameText.setText(response.referInfo.name);
        holder.referPhoneText.setText(response.referInfo.phone);
        holder.referPointText.setText( response.referInfo.point);
    }

    @Override
    public int getItemCount() {
        return local_sell_list.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, customerContactText, nameText, sellPriceText, buyPriceText;
        TextView  profitText, pointsText, referNameText, referPhoneText, referPointText;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);

            dateText = itemView.findViewById(R.id.dateTextID);
            customerContactText = itemView.findViewById(R.id.customerContactTextID);
            nameText = itemView.findViewById(R.id.nameText);
            sellPriceText = itemView.findViewById(R.id.sellPriceTextID);
            buyPriceText = itemView.findViewById(R.id.buyPriceTextID);
            profitText = itemView.findViewById(R.id.profitTextID);
            pointsText = itemView.findViewById(R.id.pointsText);
            referNameText = itemView.findViewById(R.id.referNameText);
            referPhoneText = itemView.findViewById(R.id.referPhoneText);
            referPointText = itemView.findViewById(R.id.referPointText);
        }
    }
}
