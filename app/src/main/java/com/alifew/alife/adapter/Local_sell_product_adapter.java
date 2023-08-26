package com.alifew.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.Utils.ImageHelper;
import com.alifew.alife.model.local_sell.Get_local_sell_product_response;

import java.util.List;

public class Local_sell_product_adapter extends RecyclerView.Adapter<Local_sell_product_adapter.AppViewHolder> {

    private List<Get_local_sell_product_response> productList;
    private onItemDeleteListener deleteListener;
    private onItemEditListener editListener;

    public Local_sell_product_adapter(List<Get_local_sell_product_response> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.local_sell_product_card, parent, false);
        return new Local_sell_product_adapter.AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        Get_local_sell_product_response response = productList.get(position);

        holder.productNameText.setText(response.getProduct_details());
        holder.priceText.setText(response.getPrice());

        ImageHelper.imageLoader(holder.itemView.getContext(), holder.productImage, response.getImage());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public interface onItemDeleteListener {
        void OnItemDelete(int position);
    }

    public interface onItemEditListener {
        void OnItemEdit(int position);
    }

    public void setOnClickListener(onItemDeleteListener deleteListener, onItemEditListener editListener) {
        this.deleteListener = deleteListener;
        this.editListener = editListener;
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productNameText, priceText, edit, delete;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);

            productNameText = itemView.findViewById(R.id.productNameTextID);
            productImage = itemView.findViewById(R.id.productImage);
            priceText = itemView.findViewById(R.id.priceTextID);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            editListener.OnItemEdit(position);
                        }
                    }
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            deleteListener.OnItemDelete(position);
                        }
                    }
                }
            });

        }
    }
}
