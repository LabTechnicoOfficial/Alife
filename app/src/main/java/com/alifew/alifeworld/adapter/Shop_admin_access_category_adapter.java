package com.alifew.alifeworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.Utils.ImageHelper;
import com.alifew.alifeworld.model.Category_response;

import java.util.List;

public class Shop_admin_access_category_adapter extends RecyclerView.Adapter<Shop_admin_access_category_adapter.AppViewholder> {
    private LayoutInflater layoutInflater;
    List<Category_response> categoryList;
    OnItemRemoveListener mListener;

    public Shop_admin_access_category_adapter(List<Category_response> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_admin_access_category_card, parent, false);
        return new Shop_admin_access_category_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        Category_response category = categoryList.get(position);

        ImageHelper.imageLoader(holder.itemView.getContext(), holder.categoryImage, category.getCatagory01y_logo());
        holder.categoryName.setText(category.getCatagory01y_name());
        holder.categoryUnit.setText(category.getCatagory01y_unit());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public interface OnItemRemoveListener {
        void OnItemRemove(int position);
    }

    public void setOnClickListener(OnItemRemoveListener listener1) {
        mListener = listener1;

    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView categoryImage;
        TextView categoryName, categoryUnit;
        ImageView deleteButton;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            categoryImage = (de.hdodenhof.circleimageview.CircleImageView) itemView.findViewById(R.id.categoryImageID);
            categoryName = (TextView) itemView.findViewById(R.id.categoryNameID);
            categoryUnit = (TextView) itemView.findViewById(R.id.categoryUnitID);
            deleteButton = (ImageView) itemView.findViewById(R.id.deleteID);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.OnItemRemove(position);
                        }
                    }
                }
            });


        }
    }
}
