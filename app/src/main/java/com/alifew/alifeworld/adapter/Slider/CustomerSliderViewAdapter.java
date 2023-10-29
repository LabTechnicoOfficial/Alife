package com.alifew.alifeworld.adapter.Slider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.slider.Customer_slider_response;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomerSliderViewAdapter extends SliderViewAdapter<CustomerSliderViewAdapter.ViewHolder> {
    private List<Customer_slider_response.Slider> bannerList = new ArrayList<>();

    public CustomerSliderViewAdapter(List<Customer_slider_response.Slider> bannerList) {
        this.bannerList = bannerList;
    }


    @Override
    public int getCount() {
        return bannerList.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_slider_card, null);
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

    static class ViewHolder extends SliderViewAdapter.ViewHolder {

        ImageView bannerImage;

        public ViewHolder(View itemView) {
            super(itemView);

            bannerImage = itemView.findViewById(R.id.bannerImage);

        }
    }
}
