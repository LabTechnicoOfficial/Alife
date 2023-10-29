package com.alifew.alifeworld.adapter.Barcode;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.DB.dao.ProductDao;
import com.alifew.alifeworld.DB.entity.Products;
import com.alifew.alifeworld.R;

import java.util.List;

public class Shop_product_type_view_adapter extends RecyclerView.Adapter<Shop_product_type_view_adapter.ViewHolder> {

    List<Products> typeList;
    ProductDao productDao;

    public Shop_product_type_view_adapter(List<Products> typeList, ProductDao productDao) {
        this.typeList = typeList;
        this.productDao = productDao;
    }

    @NonNull
    @Override
    public Shop_product_type_view_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_product_type_barcode_print_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Shop_product_type_view_adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Products response = typeList.get(position);
        holder.typeText.setText(response.getType());

        if (response.getPrintCheck().equals("1")) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String id = String.valueOf(response.getId());
                //
                if (isChecked) {
                    //Toast.makeText(holder.itemView.getContext(), String.valueOf(id+" "+response.getType()), Toast.LENGTH_SHORT).show();
                    productDao.updatePrintCheck(id, "1");
                } else {
                    //Toast.makeText(holder.itemView.getContext(), "OFF "+String.valueOf(id+" "+response.getType()), Toast.LENGTH_SHORT).show();
                    productDao.updatePrintCheck(id, "0");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView typeText;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox);
            typeText = itemView.findViewById(R.id.typeText);

        }
    }
}
