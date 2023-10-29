package com.alifew.alifeworld.adapter.Customer;

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

public class Customer_category_adapter extends RecyclerView.Adapter<Customer_category_adapter.AppViewholder>{
    private List<Category_response> categoryList;
    private OnItemClickListener listener;

    public Customer_category_adapter(List<Category_response> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public Customer_category_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_category_card, parent, false);
        return new Customer_category_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Customer_category_adapter.AppViewholder holder, int position) {
        Category_response categoryResponse = categoryList.get(position);

        ImageHelper.imageLoader(holder.itemView.getContext(), holder.categoryImage, categoryResponse.getCatagory01y_logo());

        holder.categoryName.setText(categoryResponse.getCatagory01y_name());
        holder.unitText.setText(categoryResponse.getCatagory01y_unit());
        holder.totalProductText.setText(categoryResponse.getTotal_product());
    }

    @Override
    public int getItemCount() {
       return categoryList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }
public void setOnClickListener(OnItemClickListener listener)
{
    this.listener=listener;
}
    public class AppViewholder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryName, unitText, totalProductText;
        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.categoryImageID);
            categoryName = itemView.findViewById(R.id.categoryLabelID);
            unitText = itemView.findViewById(R.id.categoryUnitID);
            totalProductText = itemView.findViewById(R.id.totalProductsID);
            itemView.setOnClickListener(v -> {

                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(position);
                    }
                }

            });
        }
    }
}
