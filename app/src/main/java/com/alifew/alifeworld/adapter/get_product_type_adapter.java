package com.alifew.alifeworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.get_product_type_response;

import java.util.List;

public class get_product_type_adapter extends RecyclerView.Adapter<get_product_type_adapter.AppViewholder>{
    List<get_product_type_response> product_typeList;
    private LayoutInflater layoutInflater;
    private get_product_type_adapter.OnItemClickListener mListener1;
   /* private Shop_category_adapter.OnItemEditListener mListener2;
    private Shop_category_adapter.OnItemDeleteListener mListener3;

    public ImageView editButton;
    public ImageView deleteButton;*/

    public get_product_type_adapter(List<get_product_type_response> product_typeList) {
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
   public void setOnClickListener(get_product_type_adapter.OnItemClickListener listener1) {
        mListener1 = listener1;

    }

    @Override
    public get_product_type_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.type_product, parent, false);
        return new get_product_type_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull get_product_type_adapter.AppViewholder holder, int position) {
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
            //editButton = itemView.findViewById(R.id.editButtonID);
            /*editButton.setOnClickListener(new View.OnClickListener() {
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
            deleteButton = itemView.findViewById(R.id.deleteButtonID);
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
            });*/

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
