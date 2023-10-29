package com.alifew.alifeworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.Utils.ImageHelper;
import com.alifew.alifeworld.model.Get_product_response;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Sell_product_adapter extends RecyclerView.Adapter<Sell_product_adapter.AppViewholder> {
    List<Get_product_response> productList;
    List<Get_product_response> productListAll;

    public Sell_product_adapter(List<Get_product_response> productList) {
        this.productList = productList;
        this.productListAll = new ArrayList<>();
        this.productListAll = productList;
    }

    LayoutInflater layoutInflater;
    private OnItemClickListener mListener;

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sell_product_card, parent, false);
        return new Sell_product_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        Get_product_response product = productList.get(position);

        ImageHelper.imageLoader(holder.itemView.getContext(), holder.productImage, product.getProduct_image());
        holder.productName.setText(product.getProduct_name());
        holder.stockAmount.setText(product.getStock_amount());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Get_product_response> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filterList.addAll(productListAll);
            } else {
                for (Get_product_response product_response : productListAll) {
                    String brand_code=product_response.getBrand()+product_response.getCode();
                    if ((product_response.getProduct_name().toLowerCase().contains(constraint.toString().toLowerCase())) || (product_response.getBrand().toLowerCase().contains(constraint.toString().toLowerCase()))) {
                        filterList.add(product_response);
                    }else if((brand_code.toLowerCase().contains(constraint.toString().toLowerCase())))
                    {
                        filterList.add(product_response);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productList.clear();
            productList.addAll((Collection<? extends Get_product_response>) results.values);
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
        CircularImageView productImage;
        TextView productName, stockAmount;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            productImage = (CircularImageView) itemView.findViewById(R.id.productImage);
            productName = (TextView) itemView.findViewById(R.id.productNameID);
            stockAmount = (TextView) itemView.findViewById(R.id.stockAmountID);
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
