package com.ALife.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.Custom_Type.ProductSel_type;
import com.ALife.alife.R;
import com.ALife.alife.model.get_product_type_response;

import java.util.List;

public class Shop_sell_type_select_adapter extends RecyclerView.Adapter<Shop_sell_type_select_adapter.AppViewholder> {
    private List<get_product_type_response> typeList;
    private List<ProductSel_type> productSel_types;
    private OnItemSelectListener listener;
    private OnItemAddListener listener1;
    private OnItemMinusListener listener2;

    public Shop_sell_type_select_adapter(List<get_product_type_response> typeList, List<ProductSel_type> productSel_types) {
        this.typeList = typeList;
        this.productSel_types = productSel_types;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_select_typeview_card, parent, false);
        return new Shop_sell_type_select_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        get_product_type_response type = typeList.get(position);
        ProductSel_type type_amount = productSel_types.get(position);
        holder.typeText.setText(type.getType());
        holder.amountText.setText(type.getCount());
        holder.selectedAmountText.setText(type_amount.getType_amount());
    }


    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public interface OnItemSelectListener {
        void OnTypeClick(int position);
    }

    public interface OnItemAddListener {
void OnTypeInc(int position);
    }

    public interface OnItemMinusListener {
void OnTypeDec(int position);
    }

    public void setOnClickListener(OnItemSelectListener listener,OnItemAddListener listener1,OnItemMinusListener listener2) {
        this.listener = listener;
        this.listener1=listener1;
        this.listener2=listener2;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView typeText, amountText, selectedAmountText;
        ImageView minusButton, addButton;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            typeText = itemView.findViewById(R.id.typeTextID);
            amountText = itemView.findViewById(R.id.amountTextID);
            selectedAmountText = itemView.findViewById(R.id.selectedAmountTextID);
            minusButton = itemView.findViewById(R.id.minusButtonID);
            addButton = itemView.findViewById(R.id.plusButtonID);

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnTypeClick(position);
                        }
                    }
                }
            });*/
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener1 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                          listener1.OnTypeInc(position);
                        }
                    }
                }
            });
            minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener2 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                         listener2.OnTypeDec(position);
                        }
                    }
                }
            });
        }
    }
}
