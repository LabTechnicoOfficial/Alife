package com.ALife.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.model.Fetch_product_detail_by_bar_code_response;

import java.util.ArrayList;
import java.util.List;

public class Shop_barcode_type_adapter extends RecyclerView.Adapter<Shop_barcode_type_adapter.Viewholder> {

    List<Fetch_product_detail_by_bar_code_response.Type> typeList = new ArrayList<>();
    String unit;

    public Shop_barcode_type_adapter(List<Fetch_product_detail_by_bar_code_response.Type> types, String productUnit) {
        typeList = types;
        unit = productUnit;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.barcode_product_details_card, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Shop_barcode_type_adapter.Viewholder holder, int position) {

        //Log.d("dataxx", "onBindViewHolder: "+String.valueOf(typeList.size()));
        holder.nameText.setText(typeList.get(position).type);
        holder.amountText.setText(typeList.get(position).count + " " + unit);
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView nameText, amountText;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.nameText);
            amountText = itemView.findViewById(R.id.amountText);
        }
    }
}
