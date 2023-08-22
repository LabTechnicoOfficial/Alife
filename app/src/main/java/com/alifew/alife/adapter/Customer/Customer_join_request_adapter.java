package com.alifew.alife.adapter.Customer;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.Utils.ImageHelper;
import com.alifew.alife.model.Get_shop_customer_response;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Customer_join_request_adapter extends RecyclerView.Adapter<Customer_join_request_adapter.AppViewholder>{
    LayoutInflater layoutInflater;
    List<Get_shop_customer_response> customer_request;
    private OnItemAcceptListener mListener1;
    private OnItemCancelListener mListener2;


    public Customer_join_request_adapter(List<Get_shop_customer_response> customer_request) {
        this.customer_request = customer_request;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_customer_request_card, parent, false);
        return new Customer_join_request_adapter.AppViewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        Get_shop_customer_response request=customer_request.get(position);

        ImageHelper.imageLoader(holder.itemView.getContext(),  holder.customerImage, request.getCustomer01r_image());

        holder.customerName.setText(request.getCustomer01r_name()+ " sent you connection request");


    }
    public interface OnItemAcceptListener {
        void OnItemAccept(int position);
    }
    public interface OnItemCancelListener {
        void OnItemCancel(int position);
    }
    public void ClickListener(OnItemAcceptListener listener1,OnItemCancelListener listener2)
    {
        this.mListener1=listener1;
        this.mListener2=listener2;
    }

    @Override
    public int getItemCount() {
        return customer_request.size();
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        com.mikhaellopez.circularimageview.CircularImageView customerImage;
        TextView customerName, acceptButton, cancelButton;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            customerImage = itemView.findViewById(R.id.customerImageID);
            customerName = itemView.findViewById(R.id.customerNameID);
            acceptButton = itemView.findViewById(R.id.acceptButtonID);
            cancelButton = itemView.findViewById(R.id.cancelButtonID);
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener1 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener1.OnItemAccept(position);
                        }
                    }
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener2 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener2.OnItemCancel(position);
                        }
                    }
                }
            });


        }
    }
}
