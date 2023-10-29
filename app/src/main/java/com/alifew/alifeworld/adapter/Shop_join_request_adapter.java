package com.alifew.alifeworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.Utils.ImageHelper;
import com.alifew.alifeworld.model.fetch_shop_response;

import java.util.List;

public class Shop_join_request_adapter extends RecyclerView.Adapter<Shop_join_request_adapter.AppViewholder> {
    LayoutInflater layoutInflater;
    List<fetch_shop_response> shop_request;
    private OnItemAcceptListener mListener1;
    private OnItemCancelListener mListener2;

    public Shop_join_request_adapter(List<fetch_shop_response> shop_request) {
        this.shop_request = shop_request;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_shop_request_card, parent, false);
        return new Shop_join_request_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        fetch_shop_response request = shop_request.get(position);

        ImageHelper.imageLoader(holder.itemView.getContext(), holder.shopImage, request.getStore01e_image());
        holder.shopName.setText(request.getStore01e_name());

    }

    @Override
    public int getItemCount() {
        return shop_request.size();
    }

    public interface OnItemAcceptListener {
        void OnItemAccept(int position);
    }

    public interface OnItemCancelListener {
        void OnItemCancel(int position);
    }

    public void OnClickListener(OnItemAcceptListener listener1, OnItemCancelListener listener2) {
        this.mListener1 = listener1;
        this.mListener2 = listener2;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        com.mikhaellopez.circularimageview.CircularImageView shopImage;
        TextView shopName, acceptButton, cancelButton;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            shopImage = itemView.findViewById(R.id.customerImageID);
            shopName = itemView.findViewById(R.id.customerNameID);
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
