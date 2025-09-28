package com.alifew.bcopay.adapter.Customer;

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

import com.alifew.bcopay.R;
import com.alifew.bcopay.model.customer_shopList_response;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Customer_shopList_adapter extends RecyclerView.Adapter<Customer_shopList_adapter.AppViewholder> implements Filterable {

    private LayoutInflater layoutInflater;
    public List<customer_shopList_response> shopList;
    public List<customer_shopList_response> shopListAll;


    public Customer_shopList_adapter(List<customer_shopList_response> shopList) {
        this.shopList = shopList;
        this.shopListAll = new ArrayList<>();
        this.shopListAll = shopList;
    }

    @NonNull
    @Override
    public Customer_shopList_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_shoplist_card, parent, false);
        return new Customer_shopList_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Customer_shopList_adapter.AppViewholder holder, int position) {
        customer_shopList_response shop = shopList.get(position);

        holder.shopName.setText(shop.getStore01e_name());
        holder.totalDueText.setText(shop.getTotal_due());


        Glide.with(holder.itemView.getContext())
                .load(shop.getStore01e_image())
                .centerCrop()
                .placeholder(R.drawable.loader)
                .into(holder.shopImage);

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
            List<customer_shopList_response> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filterList.addAll(shopListAll);
            } else {
                for (customer_shopList_response shop : shopListAll) {
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
            shopList.addAll((Collection<? extends customer_shopList_response>) results.values);
            notifyDataSetChanged();

        }
    };

    private OnItemUnfollowListener mListener;
    OnItemClickListener Listener;

    OnBarCodeScanClickListener mOnBarCodeScanClickListener;

    public interface OnItemUnfollowListener {
        void OnItemUnfollow(int position);
    }

    public interface OnBarCodeScanClickListener {
        void OnBarCodeScanClick(int position);
    }


    public void setOnClickListener(OnItemUnfollowListener listener, OnItemClickListener listener1, OnBarCodeScanClickListener onBarCodeScanClickListener) {
        mListener = listener;
        Listener = listener1;
        mOnBarCodeScanClickListener = onBarCodeScanClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        ImageView shopImage, barcodeScanButton;
        LinearLayout unfollowButton;
        TextView shopName, totalDueText;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            shopImage = (ImageView) itemView.findViewById(R.id.shopImageID);
            shopName = (TextView) itemView.findViewById(R.id.shopNameID);
            unfollowButton = (LinearLayout) itemView.findViewById(R.id.unfollowLayoutID);
            totalDueText = (TextView) itemView.findViewById(R.id.totalDueID);
            barcodeScanButton = itemView.findViewById(R.id.barcodeScanButton);

            unfollowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.OnItemUnfollow(position);
                        }
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Listener.OnItemClick(position);
                        }
                    }
                }
            });

            barcodeScanButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnBarCodeScanClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mOnBarCodeScanClickListener.OnBarCodeScanClick(position);
                        }
                    }
                }
            });
        }
    }
}
