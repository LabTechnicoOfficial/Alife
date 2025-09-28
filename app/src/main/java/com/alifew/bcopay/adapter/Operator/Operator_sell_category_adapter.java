package com.alifew.bcopay.adapter.Operator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class Operator_sell_category_adapter extends RecyclerView.Adapter<Operator_sell_category_adapter.AppViewholder> {
    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sell_category_card, parent, false);
        return new Operator_sell_category_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        CircularImageView categoryImage;
        TextView categoryName;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            categoryImage = (CircularImageView) itemView.findViewById(R.id.categoryImageID);
            categoryName = (TextView) itemView.findViewById(R.id.categoryNameID);
        }
    }
}
