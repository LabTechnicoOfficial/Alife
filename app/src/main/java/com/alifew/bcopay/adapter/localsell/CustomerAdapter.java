package com.alifew.bcopay.adapter.localsell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alifew.bcopay.DB.entity.Customer;
import com.alifew.bcopay.R;
import com.alifew.bcopay.Utils.ImageHelper;
import com.alifew.bcopay.view.Shop.Shop_local_sell_fragment;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends ArrayAdapter<Customer> {

    List<Customer> customerList;
    Shop_local_sell_fragment fragment;

    public CustomerAdapter(@NonNull Context context, @NonNull List<Customer> customerList, Shop_local_sell_fragment shopLocalSellFragment) {
        super(context, 0, customerList);

        this.customerList = customerList;
        this.fragment = shopLocalSellFragment;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return userFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shop_local_sell_select_customer_card, parent, false);
        }

        TextView customerName = convertView.findViewById(R.id.customerName);
        TextView customerPhone = convertView.findViewById(R.id.customerPhone);
        ImageView profileImage = convertView.findViewById(R.id.profileImage);

        Customer customer = getItem(position);

        assert customer != null;
        customerName.setText(customer.getCustomerName());
        customerPhone.setText(customer.getPhone());
        ImageHelper.imageLoader(convertView.getContext(), profileImage, customer.getImage());

        return convertView;
    }

    private final Filter userFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            List<Customer> suggestions = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                suggestions.addAll(customerList);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Customer customer : customerList) {
                    if (customer.getPhone().toLowerCase().contains(filterPattern) || customer.getCustomerName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(customer);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List) filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Customer user = (Customer) resultValue;
            //fragment.nameText.setText(user.getCustomerName());
            return user.getPhone();
        }
    };
}


