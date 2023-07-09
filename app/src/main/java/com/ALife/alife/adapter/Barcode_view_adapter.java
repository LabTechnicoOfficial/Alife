package com.ALife.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.DB.Products;
import com.ALife.alife.R;
import com.ALife.alife.Utils.Helpers;

import java.util.ArrayList;
import java.util.List;

public class Barcode_view_adapter extends RecyclerView.Adapter<Barcode_view_adapter.ViewHolder> {

    private List<Products> markedProductList = new ArrayList<>();

    public Barcode_view_adapter(List<Products> markedProductList) {
        this.markedProductList = markedProductList;
    }

    @NonNull
    @Override
    public Barcode_view_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.barcode_view_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Barcode_view_adapter.ViewHolder holder, int position) {

        Products response = markedProductList.get(position);

        holder.titleText.setText(response.getName());
        holder.barcodeImage.setImageBitmap(Helpers.barCodeGenerator(holder.itemView.getContext(), response.getBarcode()));
    }

    @Override
    public int getItemCount() {
        return markedProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        ImageView barcodeImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.titleText);
            barcodeImage=itemView.findViewById(R.id.barcodeImage);
        }
    }
}
