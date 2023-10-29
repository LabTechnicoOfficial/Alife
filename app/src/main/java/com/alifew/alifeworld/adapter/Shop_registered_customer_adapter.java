package com.alifew.alifeworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.Utils.ImageHelper;
import com.alifew.alifeworld.model.shop_due_customer_response;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class Shop_registered_customer_adapter extends  RecyclerView.Adapter<Shop_registered_customer_adapter.AppViewholder>{
    private List<shop_due_customer_response> customerList;
    LayoutInflater layoutInflater;
    private OnItemClickListener mListener;

    public Shop_registered_customer_adapter(List<shop_due_customer_response> customerList) {
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_registered_customer_card, parent, false);
        return new Shop_registered_customer_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        shop_due_customer_response customer=customerList.get(position);

        ImageHelper.imageLoader(holder.itemView.getContext(),  holder.customerImage, customer.getCustomer_image());
        holder.customerID.setText(customer.getCustomer_id());
        holder.customerName.setText(customer.getCustomer_name());
        holder.customerLocation.setText(customer.getCustomer_address());

    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public interface OnItemClickListener {
        void OnItemClickCustomer(int position);
    }
    public void setOnClickListener(OnItemClickListener mListener)
    {
        this.mListener=mListener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        CircularImageView customerImage;
        TextView customerName, customerID, customerLocation;
        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            customerImage = (CircularImageView) itemView.findViewById(R.id.customerImageID);
            customerName = (TextView) itemView.findViewById(R.id.customerNameID);
            customerID = (TextView) itemView.findViewById(R.id.customerid_ID);
            customerLocation = (TextView) itemView.findViewById(R.id.customerLocationID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.OnItemClickCustomer(position);
                        }
                    }
                }
            });
        }
    }
}
