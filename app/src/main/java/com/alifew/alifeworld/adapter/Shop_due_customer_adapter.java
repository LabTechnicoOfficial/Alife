package com.alifew.alifeworld.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

;
import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.shop_due_customer_response;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Shop_due_customer_adapter extends RecyclerView.Adapter<Shop_due_customer_adapter.AppViewholder> implements Filterable {
    List<shop_due_customer_response> customerList;
    List<shop_due_customer_response> customerListAll;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mListener1;


    public Shop_due_customer_adapter(List<shop_due_customer_response> customerList) {
        this.customerList = customerList;
        this.customerListAll = customerList;


    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<shop_due_customer_response> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filterList.addAll(customerListAll);
            } else {
                for (shop_due_customer_response customer : customerListAll) {
                    if (customer.getCustomer_phone().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
            customerList.addAll((Collection<? extends shop_due_customer_response>) results.values);
            notifyDataSetChanged();

        }
    };

    public interface OnItemClickListener {
        void OnItemClick(int position);
        //void OnItemEdit(int position);
    }


    public void setOnClickListener(OnItemClickListener listener1) {
        mListener1 = listener1;

    }


    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_due_customer_card, parent, false);
        return new AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        shop_due_customer_response customer = customerList.get(position);

        holder.lastBuyingTime.setText(customer.last_buying_time);
        holder.customer_name.setText(customer.getCustomer_name());
        holder.customer_phone.setText(customer.getCustomer_phone());
        Double all_due = Double.parseDouble(customer.getTotal_due());
        //Double all_due = Double.parseDouble(transactionList.get(i).getTotal_due());
        if (all_due >= 0.0) {
            holder.total_due_title.setText(R.string.total_due);
            holder.total_due.setText(new DecimalFormat("##.##").format(all_due));
        } else if (all_due < 0.0) {
            all_due = all_due * (-1);
            holder.total_due_title.setText(R.string.total_deposit);
            holder.total_due.setText(new DecimalFormat("##.##").format(all_due));
        }
//holder.total_due.setText(String.valueOf(new DecimalFormat("##.##").format(due_total)));

    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }


    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView customer_name, customer_phone, total_due, total_due_title, lastBuyingTime;


        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            customer_name = itemView.findViewById(R.id.customerNameID);
            customer_phone = itemView.findViewById(R.id.phoneID);
            total_due = itemView.findViewById(R.id.totalDueID);
            total_due_title = itemView.findViewById(R.id.totalDueTitle);
            lastBuyingTime = itemView.findViewById(R.id.lastBuyingTime);

            itemView.setOnClickListener(v -> {

                if (mListener1 != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener1.OnItemClick(position);
                    }
                }

            });
        }
    }


}

