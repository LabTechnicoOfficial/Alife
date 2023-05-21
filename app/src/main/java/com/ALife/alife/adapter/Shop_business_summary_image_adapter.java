package com.ALife.alife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.model.image;
import com.ALife.alife.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Shop_business_summary_image_adapter extends RecyclerView.Adapter<Shop_business_summary_image_adapter.AppViewholder> {
    List<image> imageList;
    private LayoutInflater layoutInflater;
    private OnItemClickListner1 mListener1;
    Context context;

    public Shop_business_summary_image_adapter(Context context, List<image> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    public interface OnItemClickListner1 {
        void OnItemClick1(int position);
    }

    public void setOnClickListener(OnItemClickListner1 listener1) {
        mListener1 = listener1;

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
        image image = imageList.get(position);

        // Picasso.get().load(image.getImage()).resize(400,400).centerCrop().into(holder.cardMultipleImageView);
        //  Glide.with(context).load(image.getImage()).into(holder.cardMultipleImageView);
        Picasso.get().load(image.getImage()).into(holder.cardMultipleImageView);


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
                if (mListener1 != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener1.OnItemClick1(position);
                    }
                }
            });

        }
    }

}
