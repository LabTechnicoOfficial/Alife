package com.alifew.bcopay.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.R;
import com.alifew.bcopay.Utils.ImageHelper;
import com.alifew.bcopay.model.local_sell.Get_local_sell_product_response;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        Get_local_sell_product_response response = productList.get(position);

        holder.productNameText.setText(response.getProduct_details());
        holder.priceText.setText(holder.itemView.getContext().getResources().getString(R.string.price)+": "+response.getPrice());

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
        ImageView productImage, editButton, deleteButton;
        TextView productNameText, priceText;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);

            productNameText = itemView.findViewById(R.id.productNameTextID);
            productImage = itemView.findViewById(R.id.productImage);
            priceText = itemView.findViewById(R.id.priceTextID);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton.setOnClickListener(new View.OnClickListener() {
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
            deleteButton.setOnClickListener(new View.OnClickListener() {
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
