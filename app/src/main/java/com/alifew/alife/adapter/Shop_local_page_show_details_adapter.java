package com.alifew.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.model.get_local_business_details_response;

import java.util.List;

public class Shop_local_page_show_details_adapter extends RecyclerView.Adapter<Shop_local_page_show_details_adapter.AppViewholder> {
    private List<get_local_business_details_response> data;

    public Shop_local_page_show_details_adapter(List<get_local_business_details_response> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public Shop_local_page_show_details_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_local_page_show_details_card, parent, false);
        return new Shop_local_page_show_details_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Shop_local_page_show_details_adapter.AppViewholder holder, int position) {
        //get_local_business_title_response title = titleList.get(position);
        // holder.titleText.setText(title.getTitle());
        get_local_business_details_response response = data.get(position);
        holder.amount.setText(response.getAmount());
        holder.name.setText(response.getDetails());
        holder.price.setText(response.getPrice());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView name, amount, price;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.NameId);
            amount = (TextView) itemView.findViewById(R.id.amountId);
            price = (TextView) itemView.findViewById(R.id.priceId);

        }
    }

}
