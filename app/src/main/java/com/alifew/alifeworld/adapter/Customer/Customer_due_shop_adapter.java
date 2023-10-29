package com.alifew.alifeworld.adapter.Customer;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.Utils.ImageHelper;
import com.alifew.alifeworld.model.customer_due_shop_list_response;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Customer_due_shop_adapter extends RecyclerView.Adapter<Customer_due_shop_adapter.AppViewholder> implements Filterable {
    private List<customer_due_shop_list_response> due_shop_list, All_due_shop_list;
    private OnItemClickListener listener;

    public Customer_due_shop_adapter(List<customer_due_shop_list_response> due_shop_list) {
        this.due_shop_list = due_shop_list;
        All_due_shop_list = new ArrayList<>();
        this.All_due_shop_list = due_shop_list;
    }

    @NonNull
    @Override
    public Customer_due_shop_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_due_shop_card, parent, false);
        return new Customer_due_shop_adapter.AppViewholder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Customer_due_shop_adapter.AppViewholder holder, int position) {
        customer_due_shop_list_response shop = due_shop_list.get(position);

        ImageHelper.imageLoader(holder.itemView.getContext(),  holder.shopImage, shop.getShop_image());
        holder.shopNameText.setText(shop.getShop_name());
        holder.contactText.setText(shop.getShop_phone());
        Double total_due = Double.parseDouble(shop.getTotal_due());
        if (total_due >= 0.0) {
            holder.totalDueTilte.setTextColor(R.color.black);
            holder.totalDueTilte.setText("মোট বাকিঃ");
            holder.totalDueText.setText(new DecimalFormat("##.##").format(total_due));
        } else if (total_due < 0.0) {
           total_due = total_due * (-1);
           holder.totalDueTilte.setTextColor(0xffff0000);
            holder.totalDueTilte.setText("মোট জমাঃ");
            holder.totalDueText.setTextColor(0xffff0000);
            holder.totalDueText.setText(new DecimalFormat("##.##").format(total_due));
        }

        //holder.totalDueText.setText(shop.getTotal_due());
    }

    @Override
    public int getItemCount() {
        return due_shop_list.size();
    }

    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<customer_due_shop_list_response> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filterList.addAll(All_due_shop_list);
            } else {
                for (customer_due_shop_list_response shop : All_due_shop_list) {
                    if (shop.getShop_phone().toLowerCase().contains(constraint.toString().toLowerCase()) || shop.getShop_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
            due_shop_list.clear();
            due_shop_list.addAll((Collection<? extends customer_due_shop_list_response>) results.values);
            notifyDataSetChanged();

        }
    };

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        ImageView shopImage;
        TextView shopNameText, contactText, totalDueText,totalDueTilte;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            shopImage = itemView.findViewById(R.id.shopImageID);
            shopNameText = itemView.findViewById(R.id.shopNameID);
            contactText = itemView.findViewById(R.id.contactID);
            totalDueText = itemView.findViewById(R.id.totalDueID);
            totalDueTilte=itemView.findViewById(R.id.totalDueTilte);
            itemView.setOnClickListener(v -> {

                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }

            });

        }
    }
}
