package com.ALife.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.model.fetch_sub_shop_response;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Sub_shop_adapter extends RecyclerView.Adapter<Sub_shop_adapter.AppViewholder> {

    private LayoutInflater layoutInflater;
    List<fetch_sub_shop_response> sub_shopList;
    OnItemClickListener mListener;

    public Sub_shop_adapter(List<fetch_sub_shop_response> sub_shopList) {
        this.sub_shopList = sub_shopList;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sub_shop_card, parent, false);
        return new Sub_shop_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        fetch_sub_shop_response sub_shop = sub_shopList.get(position);
        Picasso.get().load(sub_shop.getStore01e_image()).into(holder.shopImage);
        holder.shopName.setText(sub_shop.getStore01e_name());
        holder.shopLocation.setText(sub_shop.getStore01e_location());

    }

    @Override
    public int getItemCount() {
        return sub_shopList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        ImageView shopImage;
        TextView shopName, shopLocation;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            shopImage = (ImageView) itemView.findViewById(R.id.shopImageID);
            shopName = (TextView) itemView.findViewById(R.id.shopNameID);
            shopLocation = (TextView) itemView.findViewById(R.id.shopLocationID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
