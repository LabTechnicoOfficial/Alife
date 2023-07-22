package com.alifew.alife.adapter.Slider;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.adapter.Customer_coupon_adapter;
import com.alifew.alife.model.slider.SliderResponse;
import com.bumptech.glide.Glide;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;


public class ShopSliderAdapter extends RecyclerView.Adapter<ShopSliderAdapter.ViewHolder> {

    List<SliderResponse> sliderList;

    public ShopSliderAdapter(List<SliderResponse> sliderList) {
        this.sliderList = sliderList;
    }

    @NonNull
    @Override
    public ShopSliderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_slider_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopSliderAdapter.ViewHolder holder, int position) {
        SliderResponse response = sliderList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(response.bannerLink)
                .centerCrop()
                .placeholder(R.drawable.loader)
                .into(holder.sliderImage);

        holder.statusSwitch.setChecked(response.status.equals("active"));

    }

    @Override
    public int getItemCount() {
        return sliderList.size();
    }

    private SwitchChangeListener switchChangeListener;

    public interface SwitchChangeListener {
        void OnSwitchChange(int position, boolean isChecked);
    }

    public void setOnClickListener(SwitchChangeListener switchChangeListener) {
        this.switchChangeListener = switchChangeListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView sliderImage;
        SwitchMaterial statusSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sliderImage = itemView.findViewById(R.id.sliderImage);
            statusSwitch = itemView.findViewById(R.id.statusSwitch);

            statusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView.isPressed()){
                        if (switchChangeListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                switchChangeListener.OnSwitchChange(position, isChecked);
                            }
                        }
                    }
                }
            });

        }
    }
}
