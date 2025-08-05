package com.alifew.alifeworld.adapter.Slider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.slider.Customer_slider_response;
import com.bumptech.glide.Glide;


import java.util.List;

public class CustomerSliderViewAdapter extends RecyclerView.Adapter<CustomerSliderViewAdapter.ViewHolder> {
    private final List<Customer_slider_response.Slider> bannerList;

    public CustomerSliderViewAdapter(List<Customer_slider_response.Slider> bannerList) {
        this.bannerList = bannerList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_slider_card, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Customer_slider_response.Slider response = bannerList.get(position);

        Glide.with(viewHolder.itemView.getContext())
                .load(response.bannerLink)
                .centerCrop()
                .placeholder(R.drawable.loader)
                .into(viewHolder.bannerImage);
    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bannerImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bannerImage = itemView.findViewById(R.id.bannerImage);
        }
    }
}
