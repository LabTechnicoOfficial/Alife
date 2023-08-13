package com.alifew.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.model.local_sell.customer_phone_response;

import java.util.List;

public class Shop_local_sell_select_customer_adapter extends RecyclerView.Adapter<Shop_local_sell_select_customer_adapter.AppViewholder> {
    private List<customer_phone_response> phoneList;
    private LayoutInflater layoutInflater;
    private OnItemClickListener listener;

    public Shop_local_sell_select_customer_adapter(List<customer_phone_response> phoneList) {
        this.phoneList = phoneList;
    }

    @NonNull
    @Override
    public Shop_local_sell_select_customer_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_local_sell_select_customer_card, parent, false);
        return new AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Shop_local_sell_select_customer_adapter.AppViewholder holder, int position) {
        customer_phone_response customer = phoneList.get(position);
        holder.customer_phone.setText(customer.getCustomer_phone());
        if (!customer.getCustomer_name().isEmpty())
            holder.customer_name.setText(customer.getCustomer_name());

    }

    @Override
    public int getItemCount() {
        return phoneList.size();
    }

    public interface OnItemClickListener {
        void itemClick(int position);

    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView customer_name, customer_phone;
        de.hdodenhof.circleimageview.CircleImageView categoryImage;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            customer_name = itemView.findViewById(R.id.customerNameId);
            customer_phone = itemView.findViewById(R.id.customerPhoneId);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.itemClick(position);
                        }
                    }
                }
            });


        }
    }
}
