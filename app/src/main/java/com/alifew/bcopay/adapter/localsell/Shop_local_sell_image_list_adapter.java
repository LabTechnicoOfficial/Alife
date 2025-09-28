package com.alifew.bcopay.adapter.localsell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.R;
import com.alifew.bcopay.Utils.ImageHelper;

import java.util.List;

public class Shop_local_sell_image_list_adapter extends RecyclerView.Adapter<Shop_local_sell_image_list_adapter.AppViewHolder> {
    private List<String> imageList;

    public Shop_local_sell_image_list_adapter(List<String> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.multiple_image_card, parent, false);
        return new Shop_local_sell_image_list_adapter.AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        String image = imageList.get(position);

        ImageHelper.imageLoader(holder.itemView.getContext(), holder.cardMultipleImageview, image);

    }

    @Override
    public int getItemCount() {
//        if (imageList.size() > 1) {
//            return 1;
//        } else {
//            return imageList.size();
//        }
        return imageList.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        ImageView cardMultipleImageview;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);

            cardMultipleImageview = itemView.findViewById(R.id.cardMultipleImageviewID);
        }
    }
}
