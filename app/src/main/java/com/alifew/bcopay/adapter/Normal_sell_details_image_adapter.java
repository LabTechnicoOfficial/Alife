package com.alifew.bcopay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.Utils.ImageHelper;
import com.alifew.bcopay.model.image;
import com.alifew.bcopay.R;

import java.util.List;

public class Normal_sell_details_image_adapter extends RecyclerView.Adapter<Normal_sell_details_image_adapter.AppViewholder> {
    List<image> imageList;
    private LayoutInflater layoutInflater;

private ImageClickListener listener;
    public Normal_sell_details_image_adapter(List<image> imageList) {

        this.imageList = imageList;
    }


    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.multiple_image_card, parent, false);
        return new AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        image product_image = imageList.get(position);

        ImageHelper.imageLoader(holder.itemView.getContext(), holder.cardMultipleImageView, product_image.getImage());
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public interface ImageClickListener {
        void ImageClick(int position);
    }

public void setOnClickListener(ImageClickListener listener)
{
    this.listener=listener;
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
