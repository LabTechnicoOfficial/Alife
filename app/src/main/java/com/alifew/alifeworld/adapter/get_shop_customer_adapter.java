package com.alifew.alifeworld.adapter;

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

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.Utils.ImageHelper;
import com.alifew.alifeworld.model.Get_shop_customer_response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class get_shop_customer_adapter extends RecyclerView.Adapter<get_shop_customer_adapter.AppViewholder> implements Filterable {
    private LayoutInflater layoutInflater;
    List<Get_shop_customer_response> customerList;
    List<Get_shop_customer_response> customerListAll;
    private get_shop_customer_adapter.OnItemClickListener mListener1;
    private OnRemoveItemListener mListener2;
    LinearLayout removeButton;

    public get_shop_customer_adapter(List<Get_shop_customer_response> customerList) {
        this.customerList = customerList;
        this.customerListAll = new ArrayList<>();
        this.customerListAll = customerList;
    }


    @NonNull
    @Override
    public get_shop_customer_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customers_card, parent, false);
        return new get_shop_customer_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull get_shop_customer_adapter.AppViewholder holder, int position) {
        Get_shop_customer_response customer = customerList.get(position);

        ImageHelper.imageLoader(holder.itemView.getContext(), holder.customerImage, customer.getCustomer01r_image());
        holder.customerName.setText(customer.getCustomer01r_name());
        holder.customerLocation.setText(customer.getCustomer01r_address());
       // Double totaldue = Double.parseDouble(customer.getTotal_due());
        //holder.total_due.setText(String.valueOf(new DecimalFormat("##.##").format(totaldue)));
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Get_shop_customer_response> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filterList.addAll(customerListAll);
            } else {
                for (Get_shop_customer_response customer : customerListAll) {
                    if (customer.getCustomer01r_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterList.add(customer);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            customerList.clear();
            customerList.addAll((Collection<? extends Get_shop_customer_response>) results.values);
            notifyDataSetChanged();

        }
    };

    public interface OnItemClickListener {
        void OnItemClick(int position);
        //void OnItemEdit(int position);

    }

    public void setOnClickListener(OnItemClickListener listener1, OnRemoveItemListener listener2) {
        mListener1 = listener1;
        mListener2 = listener2;
    }

    public interface OnRemoveItemListener {
        void OnRemoveItem(int position);
    }


    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView customerName, customerLocation, total_due;
        ImageView customerImage;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerNameID);
            customerLocation = itemView.findViewById(R.id.customerLocationID);
           // total_due = itemView.findViewById(R.id.totalDueID);
            customerImage = itemView.findViewById(R.id.customerImageID);
            removeButton = itemView.findViewById(R.id.removeLayoutID);

            itemView.setOnClickListener(v -> {
                if (mListener1 != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener1.OnItemClick(position);
                    }
                }
            });

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener2 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener2.OnRemoveItem(position);
                        }
                    }
                }
            });

        }
    }
}
