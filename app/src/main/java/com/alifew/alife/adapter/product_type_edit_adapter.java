package com.alifew.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.model.get_product_type_response;

import java.util.List;

public class product_type_edit_adapter extends RecyclerView.Adapter<product_type_edit_adapter.AppViewholder>{
    List<get_product_type_response> product_typeList;
    private LayoutInflater layoutInflater;
    private product_type_edit_adapter.OnItemClickListener mListener1;
 private product_type_edit_adapter.OnItemEditListener mListener2;
    private product_type_edit_adapter.OnItemDeleteListener mListener3;

    public ImageView editButton;
    public ImageView deleteButton;

    public product_type_edit_adapter(List<get_product_type_response> product_typeList) {
        this.product_typeList = product_typeList;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
        //void OnItemEdit(int position);
    }
    public interface OnItemEditListener {
        void OnItemEdit(int position);
        //void OnItemEdit(int position);
    }

    public interface OnItemDeleteListener {
        void OnItemDelete(int position);
    }
    public void setOnClickListener(product_type_edit_adapter.OnItemClickListener listener1,product_type_edit_adapter.OnItemEditListener listener2,product_type_edit_adapter.OnItemDeleteListener listener3) {
        mListener1 = listener1;
        mListener2=listener2;
        mListener3=listener3;

    }

    @Override
    public product_type_edit_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.type_product_edit, parent, false);
        return new product_type_edit_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull product_type_edit_adapter.AppViewholder holder, int position) {
        get_product_type_response product_type = product_typeList.get(position);
        holder.type.setText(product_type.getType());
        holder.count.setText(product_type.getCount());
    }

    @Override
    public int getItemCount() {
        return product_typeList.size();
    }


    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView type,count;


        public AppViewholder(@NonNull View itemView) {
            super(itemView);


            type = itemView.findViewById(R.id.type);
            count = itemView.findViewById(R.id.count);
            editButton = itemView.findViewById(R.id.editButton);
          editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener2 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener2.OnItemEdit(position);
                        }
                    }
                }
            });
            deleteButton = itemView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener3 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener3.OnItemDelete(position);
                        }
                    }
                }
            });

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
