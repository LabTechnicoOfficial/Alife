package com.alifew.bcopay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.R;
import com.alifew.bcopay.Utils.ImageHelper;
import com.alifew.bcopay.model.fetch_shop_admin_response;

import java.util.List;

public class Shop_admin_adapter extends RecyclerView.Adapter<Shop_admin_adapter.AppViewholder> {
    private LayoutInflater layoutInflater;
    List<fetch_shop_admin_response> adminList;
    private OnItemClickListener mListener;
    private OnItemDeleteListener mListener2;
    private OnItemActiveListener mListener3;
    private Context context;
    String admin_roll;

    private fetch_shop_admin_response admin;

    public Shop_admin_adapter(List<fetch_shop_admin_response> adminList, Context context) {
        this.adminList = adminList;
        this.context = context;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_admin_card, parent, false);
        return new Shop_admin_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        admin = adminList.get(position);

        ImageHelper.imageLoader(holder.itemView.getContext(),  holder.adminImage, admin.getAgent_image());
        holder.adminName.setText(admin.getAgent_name());
        if(admin.getAgent_access().equals("1")){
            admin_roll = "Assistant";
        }else if(admin.getAgent_access().equals("2")){
            admin_roll = "Manager";
        }

        holder.adminRoll.setText(admin_roll);

        if (admin.getStatus().equals("0")) {
            holder.activeInactiveStatus.setText("Active");
            holder.activeInactiveStatus.setTextColor(ContextCompat.getColor(context, R.color.pink));
        } else if (admin.getStatus().equals("1")) {
            holder.activeInactiveStatus.setText("Inactive");
        }

    }

    @Override
    public int getItemCount() {
        return adminList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public interface OnItemDeleteListener {
        void OnItemDelete(int position);
    }

    public interface OnItemActiveListener {
        void OnItemActive(int position);
    }

    public void setOnClickListener(OnItemClickListener listener1, OnItemDeleteListener listener2, OnItemActiveListener listener3) {
        mListener = listener1;
        mListener2 = listener2;
        mListener3 = listener3;

    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView adminImage;
        TextView adminName, activeInactiveStatus, adminRoll;
        ImageView deleteButton;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            adminImage = (de.hdodenhof.circleimageview.CircleImageView) itemView.findViewById(R.id.adminImageID);
            adminName = (TextView) itemView.findViewById(R.id.adminNameID);
            activeInactiveStatus = (TextView) itemView.findViewById(R.id.activeInactiveStatusID);
            deleteButton = (ImageView) itemView.findViewById(R.id.deleteID);
            adminRoll = (TextView) itemView.findViewById(R.id.adminRollNameID);

            activeInactiveStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener3 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener3.OnItemActive(position);
                        }
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener2 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener2.OnItemDelete(position);
                        }
                    }
                }
            });

            itemView.setOnClickListener(v -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.OnItemClick(position);
                    }
                }
            });
        }
    }
}
