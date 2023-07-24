package com.alifew.alife.adapter.Barcode;

import android.annotation.SuppressLint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.DB.Products;
import com.alifew.alife.R;
import com.alifew.alife.Utils.Helpers;

import java.util.ArrayList;
import java.util.List;

public class Barcode_view_adapter extends RecyclerView.Adapter<Barcode_view_adapter.ViewHolder> {

    private List<Products> markedProductList = new ArrayList<>();
    private String shopName;
    int divider;

    public Barcode_view_adapter(List<Products> markedProductList, String shopName, int divider) {
        this.markedProductList = markedProductList;
        this.shopName = shopName;
        this.divider = divider;
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
        holder.barcodeImage.getLayoutParams().height = 220/divider;


       // holder.availableTypeText.setText("Size: "+Html.fromHtml("<b>" + response.getType()+"<b>"));

//        if (response.getType().isEmpty()){
//            holder.availableTypeText.setVisibility(View.INVISIBLE);
//        }else {
//            holder.availableTypeText.setVisibility(View.VISIBLE);
//
//            sizeText = Html.fromHtml("Size: <b>" + response.getType()+"<b>").toString();
//        }

        String sizeText=  !response.getType().isEmpty() ? Html.fromHtml("&emsp; Size: <b>" + response.getType()+"</b>").toString() : "";

        holder.sellingPriceText.setText(Html.fromHtml("Price: <b>" + response.getPrice()+"</b> tk")+sizeText);
        holder.sponsorText.setText(Html.fromHtml("<i>Powered by</i> " + "<font color='red'><b>ALIFE</b></font>"));

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
