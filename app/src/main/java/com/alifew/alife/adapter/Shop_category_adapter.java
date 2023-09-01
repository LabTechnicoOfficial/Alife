package com.alifew.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

;
import com.alifew.alife.R;
import com.alifew.alife.Utils.ImageHelper;
import com.alifew.alife.model.Category_response;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Shop_category_adapter extends RecyclerView.Adapter<Shop_category_adapter.AppViewholder> implements Filterable {
    List<Category_response> categoryList;
    List<Category_response> categoryListAll;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mListener1, mListener4;
    private OnItemEditListener mListener2;
    private OnItemDeleteListener mListener3;
    public ImageView editButton;
    public ImageView deleteButton;
    private String type;


    public Shop_category_adapter(List<Category_response> categoryList, String type) {
        this.categoryList = categoryList;
        this.categoryListAll = new ArrayList<>();
        this.categoryListAll = categoryList;
        this.type = type;

    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Category_response> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filterList.addAll(categoryListAll);
            } else {
                for (Category_response category : categoryListAll) {
                    if (category.getCatagory01y_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterList.add(category);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            categoryList.clear();
            categoryList.addAll((Collection<? extends Category_response>) results.values);
            notifyDataSetChanged();

        }
    };

    public interface OnItemClickListener {
        void OnItemClick(int position);
        //void OnItemEdit(int position);
    }

    public interface OnItemEditListener {
        void OnItemEdit(int position);
        //void OnItemEdit(int position);
    }

    public interface OnItemDeleteListener {
        void OnItemDelete(int position);
    }

    public void setOnClickListener(OnItemClickListener listener1, OnItemEditListener listener2, OnItemDeleteListener listener3) {
        mListener1 = listener1;
        mListener2 = listener2;
        mListener3 = listener3;
    }

    public void setOnClickListener1(OnItemClickListener listener1) {
        this.mListener4 = listener1;
    }

    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.category_card, parent, false);
        return new AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        Category_response category = categoryList.get(position);

        ImageHelper.imageLoader(holder.itemView.getContext(), holder.categoryImage, category.getCatagory01y_logo());
        holder.category.setText(category.getCatagory01y_name());
        holder.categoryUnit.setText(category.getCatagory01y_unit());
        holder.total_items.setText(category.getTotal_product());
        holder.total_sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(category.getTotal_sell_price()))));
        // holder.total_profit.setText(category.getTotal_profit());
        holder.total_products.setText(category.getTotal_stock_product());
        holder.total_profit.setText(String.valueOf(new DecimalFormat("##.##").format(Double.parseDouble(category.getTotal_profit()))));

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }


    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView category, categoryUnit, total_items, total_profit, total_sell_price, edit, delete, total_products;
        de.hdodenhof.circleimageview.CircleImageView categoryImage;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.categoryImageID);
            category = itemView.findViewById(R.id.categorylabel);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            categoryUnit = itemView.findViewById(R.id.categoryUnitID);
            total_items = itemView.findViewById(R.id.totalItemsID);
            total_products = itemView.findViewById(R.id.totalProductsID);
            total_profit = itemView.findViewById(R.id.totalProfitID);
            total_sell_price = itemView.findViewById(R.id.totalSellPriceID);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            if (type.equals("admin")) {
                editButton.setVisibility(View.INVISIBLE);
                deleteButton.setVisibility(View.INVISIBLE);
                edit.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);

            }

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener2 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener2.OnItemEdit(position);
                        }
                    }
                }
            });


            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener3 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener3.OnItemDelete(position);
                        }
                    }
                }
            });


            itemView.setOnClickListener(v -> {
                if (type.equals("admin")) {
                    if (mListener4 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener4.OnItemClick(position);
                        }
                    }
                } else {
                    if (mListener1 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener1.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }


}
