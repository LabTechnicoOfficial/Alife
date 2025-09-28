package com.alifew.bcopay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.R;
import com.alifew.bcopay.model.get_product_offer_response;

import java.text.DecimalFormat;
import java.util.List;

public class get_product_offer_adapter extends RecyclerView.Adapter<get_product_offer_adapter.AppViewholder> {
    List<get_product_offer_response> product_offerList;
    private LayoutInflater layoutInflater;
    private get_product_offer_adapter.OnItemClickListener mListener1;
    private String unit;
    private String product_price;

    public get_product_offer_adapter(List<get_product_offer_response> product_offerList, String unit, String product_price) {
        this.product_offerList = product_offerList;
        this.unit = unit;
        this.product_price = product_price;
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

    public void setOnClickListener(get_product_offer_adapter.OnItemClickListener listener1) {
        mListener1 = listener1;

    }

    @Override
    public get_product_offer_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycleview_offer, parent, false);
        return new get_product_offer_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull get_product_offer_adapter.AppViewholder holder, int position) {
        get_product_offer_response product_type = product_offerList.get(position);
        holder.amount.setText(product_type.getAmount() + " " + unit);

        String percentage = product_type.getPrice();
        holder.percentage.setText(percentage+"%");
        Double total_price = Double.parseDouble(product_type.getAmount()) * Double.parseDouble(product_price);
        Double offer_price = total_price - (total_price * (Double.parseDouble(percentage) / 100));
        holder.price.setText(String.valueOf(new DecimalFormat("##.##").format(offer_price)));
    }

    @Override
    public int getItemCount() {
        return product_offerList.size();
    }


    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView amount, percentage, price;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);


            amount = itemView.findViewById(R.id.amount);
            percentage = itemView.findViewById(R.id.percentage);
            price = itemView.findViewById(R.id.price);
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
