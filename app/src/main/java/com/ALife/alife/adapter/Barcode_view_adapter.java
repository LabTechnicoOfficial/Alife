package com.ALife.alife.adapter;

import android.text.Html;
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
        holder.barcodeText.setText(response.getBarcode());
        holder.sellingPriceText.setText("Price: " + response.getPrice()+" tk");
        holder.stockAmountText.setText("Amount: " + response.getStock() + " " + response.getUnit());
        String sourceString = "Powered by " + "<b>ALIFE</b> ";
        holder.sponsorText.setText(Html.fromHtml(sourceString));

        // holder.barcodeText.setTextScaleX((float) 1.6 );
    }

    @Override
    public int getItemCount() {
        return markedProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, barcodeText, stockAmountText, sellingPriceText, sponsorText;
        ImageView barcodeImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.titleText);
            barcodeText = itemView.findViewById(R.id.barcodeText);
            stockAmountText = itemView.findViewById(R.id.stockAmountText);
            sellingPriceText = itemView.findViewById(R.id.sellingPriceText);
            sponsorText = itemView.findViewById(R.id.sponsorText);
            barcodeImage = itemView.findViewById(R.id.barcodeImage);
        }
    }
}
