package com.alifew.alife.adapter.Customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.model.fetch_shop_response;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Customer_allShop_adapter extends RecyclerView.Adapter<Customer_allShop_adapter.AppViewholder> implements Filterable {
    private LayoutInflater layoutInflater;
    List<fetch_shop_response> shopList;
    List<fetch_shop_response> shopListAll;
    private OnItemFollowListener mListener;

    public Customer_allShop_adapter(List<fetch_shop_response> shopList) {
        this.shopList = shopList;
        this.shopListAll = new ArrayList<>();
        this.shopListAll = shopList;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_all_shop_card, parent, false);
        return new Customer_allShop_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        fetch_shop_response shop = shopList.get(position);
        Picasso.get().load(shop.getStore01e_image()).into(holder.shopImage);
        holder.shopName.setText(shop.getStore01e_name());

    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<fetch_shop_response> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filterList.addAll(shopListAll);
            } else {
                for (fetch_shop_response shop : shopListAll) {
                    if (shop.getStore01e_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterList.add(shop);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            shopList.clear();
            shopList.addAll((Collection<? extends fetch_shop_response>) results.values);
            notifyDataSetChanged();

        }
    };
    public void setOnClickListener(OnItemFollowListener listener) {
        mListener = listener;

    }
    public interface OnItemFollowListener {
        void OnItemFollow(int position);
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        ImageView shopImage;
        LinearLayout followButton;
        TextView shopName;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            shopImage = (ImageView) itemView.findViewById(R.id.shopImageID);
            shopName = (TextView) itemView.findViewById(R.id.shopNameID);
            followButton = (LinearLayout) itemView.findViewById(R.id.followLayoutID);

            followButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.OnItemFollow(position);
                        }
                    }
                }
            });
        }
    }
}
