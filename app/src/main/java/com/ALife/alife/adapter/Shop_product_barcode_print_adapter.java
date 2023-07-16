package com.ALife.alife.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.DB.Products;
import com.ALife.alife.R;
import com.ALife.alife.model.Get_product_response;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Shop_product_barcode_print_adapter extends RecyclerView.Adapter<Shop_product_barcode_print_adapter.ViewHolder> {
    private List<Products> productList = new ArrayList<>();

    public Shop_product_barcode_print_adapter(List<Products> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public Shop_product_barcode_print_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_product_barcode_print_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Shop_product_barcode_print_adapter.ViewHolder holder, int position) {
        Products response = productList.get(position);

        Picasso.get().load(response.getImage()).into(holder.productImage);

        holder.titleText.setText(response.getName());

        if (response.getType().isEmpty()) {
            holder.typeText.setVisibility(View.GONE);
        } else {
            holder.typeText.setVisibility(View.VISIBLE);
            holder.typeText.setText(Html.fromHtml("Type: <b>" + response.getType() + "<b>"));
        }

        holder.priceText.setText(Html.fromHtml("Price: <b>" + response.getPrice() + "<b> tk"));

        if (response.getPrintCheck().equals("1")) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    Toast.makeText(holder.itemView.getContext(), response.getProductID(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(holder.itemView.getContext(), response.getProductID(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private OnCheckBoxClickListener onCheckBoxClickListener;

    public interface OnCheckBoxClickListener {
        void onCheckBoxClick(int position, boolean state);
    }

    public void setOnClickListener(OnCheckBoxClickListener onCheckBoxClickListener) {
        this.onCheckBoxClickListener = onCheckBoxClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView titleText, typeText, priceText;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            titleText = itemView.findViewById(R.id.titleText);
            typeText = itemView.findViewById(R.id.typeText);
            priceText = itemView.findViewById(R.id.priceText);
            checkBox = itemView.findViewById(R.id.checkBox);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (onCheckBoxClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onCheckBoxClickListener.onCheckBoxClick(position, isChecked);
                        }
                    }
                }
            });
        }
    }
}
