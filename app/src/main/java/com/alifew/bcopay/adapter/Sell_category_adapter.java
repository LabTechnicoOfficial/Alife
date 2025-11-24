package com.alifew.bcopay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.R;
import com.alifew.bcopay.Utils.ImageHelper;
import com.alifew.bcopay.model.Category_response;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Sell_category_adapter extends RecyclerView.Adapter<Sell_category_adapter.AppViewholder> {
    LayoutInflater layoutInflater;
    private List<Category_response> categoryList;
    private List<Category_response> categoryList_All;
    private OnItemClickListener mListener;

    public Sell_category_adapter(List<Category_response> categoryList) {
        this.categoryList = categoryList;
        categoryList_All = new ArrayList<>();
        this.categoryList_All = categoryList;
    }

    @NonNull
    @Override
    public Sell_category_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sell_category_card, parent, false);
        return new Sell_category_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Sell_category_adapter.AppViewholder holder, int position) {
        Category_response category = categoryList.get(position);

        ImageHelper.imageLoader(holder.itemView.getContext(), holder.categoryImage, category.getCatagory01y_logo());
        holder.categoryName.setText(category.getCatagory01y_name());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public Filter getFilter() {

        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Category_response> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filterList.addAll(categoryList_All);
            } else {
                for (Category_response category : categoryList_All) {
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
    }

    public void setOnClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        CircularImageView categoryImage;
        TextView categoryName;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            categoryImage = (CircularImageView) itemView.findViewById(R.id.categoryImageID);
            categoryName = (TextView) itemView.findViewById(R.id.categoryNameID);
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
