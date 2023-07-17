package com.ALife.alife.adapter.Barcode;

import android.annotation.SuppressLint;
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
    private String shopName;

    public Barcode_view_adapter(List<Products> markedProductList, String shopName) {
        this.markedProductList = markedProductList;
        this.shopName = shopName;
    }

    @NonNull
    @Override
    public Barcode_view_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.barcode_view_card, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Barcode_view_adapter.ViewHolder holder, int position) {

        Products response = markedProductList.get(position);

        holder.titleText.setText(response.getName());
        holder.barcodeImage.setImageBitmap(Helpers.barCodeGenerator(holder.itemView.getContext(), response.getBarcode()));
        holder.barcodeText.setText(response.getBarcode());

        String sizeText = "";

        if (response.getType().isEmpty()){
            holder.availableTypeText.setVisibility(View.INVISIBLE);
        }else {
            holder.availableTypeText.setVisibility(View.INVISIBLE);
            //holder.availableTypeText.setText(Html.fromHtml("Size: <b>" + response.getType()+"<b>"));
            sizeText = Html.fromHtml("Size: <b>" + response.getType()+"<b>").toString();
        }

        holder.sellingPriceText.setText(Html.fromHtml("Price: <b>" + response.getPrice()+"<b> tk")+" "+sizeText);
        String sourceString = "<i>Powered by</i> " + "<b>ALIFE</b>";
        holder.sponsorText.setText(Html.fromHtml(sourceString));

        holder.shopNameText.setText(shopName);

    }

    @Override
    public int getItemCount() {
        return markedProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, barcodeText, availableTypeText, sellingPriceText, sponsorText, shopNameText;
        ImageView barcodeImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.titleText);
            barcodeText = itemView.findViewById(R.id.barcodeText);
            availableTypeText = itemView.findViewById(R.id.availableTypeText);
            sellingPriceText = itemView.findViewById(R.id.sellingPriceText);
            sponsorText = itemView.findViewById(R.id.sponsorText);
            shopNameText = itemView.findViewById(R.id.shopNameText);
            barcodeImage = itemView.findViewById(R.id.barcodeImage);
        }
    }
}
