package com.alifew.alife.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;

import java.util.List;

public class Normal_sell_product_image_show_adapter extends RecyclerView.Adapter<Normal_sell_product_image_show_adapter.AppViewholder> {
    List<Bitmap> imageList;
    private LayoutInflater layoutInflater;
private OnItemClickListener listener;

    public Normal_sell_product_image_show_adapter( List<Bitmap> imageList) {

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
       Bitmap image = imageList.get(position);
       holder.cardMultipleImageView.setImageBitmap(image);

        // Picasso.get().load(image.getImage()).resize(400,400).centerCrop().into(holder.cardMultipleImageView);





    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public interface OnItemClickListener {
         void OnItemClick(int position);
    }
public void setOnClickListener(OnItemClickListener listener)
{
    this.listener=listener;
}

    public class AppViewholder extends RecyclerView.ViewHolder {
        ImageView cardMultipleImageView;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            cardMultipleImageView = (ImageView) itemView.findViewById(R.id.cardMultipleImageviewID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });


        }
    }

}
