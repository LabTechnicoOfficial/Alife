package com.alifew.alifeworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.Custom_Type.ProductSel_type;
import com.alifew.alifeworld.R;
import com.alifew.alifeworld.model.get_product_type_response;

import java.util.List;

public class Shop_sellamount_inc_dec_adapter extends RecyclerView.Adapter<Shop_sellamount_inc_dec_adapter.AppViewholder> {
    private List<get_product_type_response> types;
    private List<ProductSel_type> sel_types;
    private String operation;
    private addListener listener1;
    private minusListener listener2;

    public Shop_sellamount_inc_dec_adapter(List<get_product_type_response> types, List<ProductSel_type> sel_types, String operation) {
        this.types = types;
        this.sel_types = sel_types;
        this.operation = operation;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_select_typeview_card, parent, false);
        return new Shop_sellamount_inc_dec_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {

        if(operation.equals("add"))
        {
            get_product_type_response type=types.get(position);
            int check=0;
            holder.typeText.setText(type.getType());
            holder.amountText.setText(type.getCount());
            for(int i=0;i<sel_types.size();i++)
            {
                if(type.getId().equals(sel_types.get(i).getType_id()))
                {
                    holder.selectedAmountText.setText(sel_types.get(i).getType_amount());
                    check=1;
                    break;
                }
            }
            if(check==0)
            {
                holder.selectedAmountText.setText("0");
            }
        }
        else if(operation.equals("minus"))
        {
            ProductSel_type type=sel_types.get(position);
            for(int i=0;i<sel_types.size();i++)
            {
                if(type.getType_id().equals(types.get(i).getId()))
                {
                    holder.typeText.setText(types.get(i).getType());
                    holder.amountText.setText(types.get(i).getCount());
                    holder.selectedAmountText.setText(type.getType_amount());
                    break;
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        int size;
        if(operation.equals("add")) {
            return types.size();
        }
        else
        {
            return sel_types.size();
        }
    }

    public interface addListener {
        void increament(int position);
    }

    public interface minusListener {
        void decreament(int position);
    }

    public void setOnClickListener(addListener listener1, minusListener listener2) {
        this.listener1 = listener1;
        this.listener2 = listener2;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView typeText, amountText, selectedAmountText;
ImageView plus,minus;
        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            typeText = itemView.findViewById(R.id.typeTextID);
            amountText = itemView.findViewById(R.id.amountText);
            selectedAmountText = itemView.findViewById(R.id.selectedAmountTextID);
            plus=itemView.findViewById(R.id.plusButtonID);
            minus=itemView.findViewById(R.id.minusButtonID);
            plus.setVisibility(View.INVISIBLE);
            minus.setVisibility(View.INVISIBLE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(operation.equals("add")) {
                        if (listener1 != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener1.increament(position);
                            }
                        }
                    }
                    else
                    {
                        if (listener2 != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener2.decreament(position);
                            }
                        }
                    }
                }
            });
        }
    }
}
