package com.alifew.bcopay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.R;
import com.alifew.bcopay.model.shop_sell_history_list_response;

import java.util.List;

public class Shop_sell_history_main_adapter extends RecyclerView.Adapter<Shop_sell_history_main_adapter.AppViewholder> {

    List<shop_sell_history_list_response> sell_list;
    private onItemClickListener listener;

    public Shop_sell_history_main_adapter(List<shop_sell_history_list_response> sell_list) {
        this.sell_list = sell_list;
    }

    @NonNull
    @Override
    public Shop_sell_history_main_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_sell_history_main_card, parent, false);
        return new Shop_sell_history_main_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Shop_sell_history_main_adapter.AppViewholder holder, int position) {
        shop_sell_history_list_response response = sell_list.get(position);

        holder.productName.setText(response.getProduct_name());
        holder.productId.setText(response.getProduct_id());
        holder.productAmount.setText(response.getProduct_amount());
        holder.productSellPrice.setText(response.getSell_price());
        holder.profitText.setText(response.getProfit());
        holder.dateText.setText(response.getDate());

    }

    @Override
    public int getItemCount() {
        return sell_list.size();
    }

    public interface onItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {

        TextView productId, productName, productAmount, productSellPrice, dateText, profitText;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            productId = itemView.findViewById(R.id.productIdID);
            productName = itemView.findViewById(R.id.productNameID);

            productAmount = itemView.findViewById(R.id.productAmountID);
            productSellPrice = itemView.findViewById(R.id.productSellPriceID);
            profitText = itemView.findViewById(R.id.profitTextID);
            dateText = itemView.findViewById(R.id.dateTextID);
            itemView.setOnClickListener(v -> {

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
