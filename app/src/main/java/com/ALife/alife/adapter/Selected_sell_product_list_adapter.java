package com.ALife.alife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.Custom_Type.ProductSell;
import com.ALife.alife.R;
import com.ALife.alife.viewmodel.Get_product;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class Selected_sell_product_list_adapter extends RecyclerView.Adapter<Selected_sell_product_list_adapter.AppViewholder> {
    private LayoutInflater layoutInflater;
    private List<ProductSell> productSellList;
    private Context context;
    private Get_product get_product;
    private int cart_state;
    private OnItemAddListener mListener1;
    private OnItemMinusListener mListener2;
    private OnItemRemoveListener mListener3;

    public Selected_sell_product_list_adapter(List<ProductSell> productSellList, Context context,int cart_state) {
        this.productSellList = productSellList;
        this.context = context;
        this.cart_state=cart_state;
    }

    @NonNull
    @Override
    public Selected_sell_product_list_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.selected_product_list_card, parent, false);
        return new Selected_sell_product_list_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Selected_sell_product_list_adapter.AppViewholder holder, int position) {
        ProductSell productSell = productSellList.get(position);
        String productId = productSell.getProduct_id();
        String amount=productSell.getAmount();
        Picasso.get().load(productSell.getProduct_image()).into(holder.productImage);
        /*if (!productSell.getOffer_id().equals("0")) {
            holder.increament.setVisibility(View.GONE);
            holder.decreament.setVisibility(View.GONE);
            amount=amount+"(offer)";

        }*/
        holder.productName.setText(productSell.getProduct_name());
        holder.productAmount.setText(amount);
        holder.productType.setText(productSell.getType_name());
        Double product_price=Double.parseDouble(productSell.getPrice());

       // holder.productPrice.setText(productSell.getPrice());
        holder.productPrice.setText(String.valueOf(new DecimalFormat("##.##").format(product_price)));




    }

    public interface OnItemAddListener {
        void OnItemAdd(int position);
        //void OnItemEdit(int position);
    }

    public interface OnItemMinusListener {
        void OnItemMinus(int position);
        //void OnItemEdit(int position);
    }

    public interface OnItemRemoveListener {
        void OnItemRemove(int position);
        //void OnItemEdit(int position);
    }

    public void setOnClickListener(OnItemAddListener mListener1, OnItemMinusListener mListener2, OnItemRemoveListener mListener3) {
        this.mListener1 = mListener1;
        this.mListener2 = mListener2;
        this.mListener3 = mListener3;
    }

    @Override
    public int getItemCount() {
        return productSellList.size();
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        CircularImageView productImage;
        ImageView increament, decreament, remove;
        TextView productName, productType, productAmount, productPrice;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            productImage = (CircularImageView) itemView.findViewById(R.id.productImageID);
            productName = (TextView) itemView.findViewById(R.id.productNameID);
            productType = (TextView) itemView.findViewById(R.id.typeID);
            productAmount = (TextView) itemView.findViewById(R.id.amountID);
            productPrice = (TextView) itemView.findViewById(R.id.priceID);
            increament = (ImageView) itemView.findViewById(R.id.plusButtonID);
            decreament = (ImageView) itemView.findViewById(R.id.minusButtonID);
            remove = (ImageView) itemView.findViewById(R.id.removeItem);
            productType.setVisibility(View.GONE);
if(cart_state==2)
{
    increament.setVisibility(View.GONE);
    decreament.setVisibility(View.GONE);

    remove.setVisibility(View.GONE);
}
            increament.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener1 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener1.OnItemAdd(position);
                        }
                    }
                }
            });
            decreament.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener2 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener2.OnItemMinus(position);
                        }
                    }
                }
            });
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener3 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener3.OnItemRemove(position);
                        }
                    }
                }
            });

        }
    }
}
