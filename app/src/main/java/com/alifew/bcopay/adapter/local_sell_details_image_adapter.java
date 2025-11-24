package com.alifew.bcopay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.R;
import com.alifew.bcopay.Utils.ImageHelper;
import com.alifew.bcopay.model.local_sell.local_sell_image;

import java.util.List;

public class local_sell_details_image_adapter extends RecyclerView.Adapter<local_sell_details_image_adapter.AppViewholder>{
    List<local_sell_image> imageList;
    private LayoutInflater layoutInflater;

    private Normal_sell_details_image_adapter.ImageClickListener listener;

    public local_sell_details_image_adapter(List<local_sell_image> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public local_sell_details_image_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.multiple_image_card, parent, false);
        return new local_sell_details_image_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull local_sell_details_image_adapter.AppViewholder holder, int position) {
        local_sell_image product_image = imageList.get(position);

        ImageHelper.imageLoader(holder.itemView.getContext(),  holder.cardMultipleImageView, product_image.getProduct_image());
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
    public class AppViewholder extends RecyclerView.ViewHolder {
        ImageView cardMultipleImageView;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            cardMultipleImageView = (ImageView) itemView.findViewById(R.id.cardMultipleImageviewID);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.ImageClick(position);
                    }
                }
            });

        }
    }
}
