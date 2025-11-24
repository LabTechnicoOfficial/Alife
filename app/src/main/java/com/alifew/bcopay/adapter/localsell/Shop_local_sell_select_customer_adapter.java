package com.alifew.bcopay.adapter.localsell;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.DB.entity.Customer;
import com.alifew.bcopay.R;
import com.alifew.bcopay.Utils.ImageHelper;

import java.util.List;

public class Shop_local_sell_select_customer_adapter extends RecyclerView.Adapter<Shop_local_sell_select_customer_adapter.AppViewholder> {
    private List<Customer> customerList;
    private OnItemClickListener listener;

    public Shop_local_sell_select_customer_adapter(List<Customer> customerList) {
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public Shop_local_sell_select_customer_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new AppViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_local_sell_select_customer_card, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Shop_local_sell_select_customer_adapter.AppViewholder holder, int position) {
        Customer response = customerList.get(position);
        holder.customer_phone.setText(holder.itemView.getContext().getResources().getString(R.string.phone)+": "+response.getPhone());
        holder.customer_name.setText(response.getCustomerName());
        ImageHelper.imageLoader(holder.itemView.getContext(), holder.profileImage, response.getImage());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public interface OnItemClickListener {
        void customerItemClick(int position);

    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView customer_name, customer_phone;
        ImageView profileImage;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            customer_name = itemView.findViewById(R.id.customerName);
            customer_phone = itemView.findViewById(R.id.customerPhone);
            profileImage = itemView.findViewById(R.id.profileImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.customerItemClick(position);
                        }
                    }
                }
            });


        }
    }
}
