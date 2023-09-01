package com.alifew.alife.adapter.Operator;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.Utils.ImageHelper;
import com.alifew.alife.model.Get_product_response;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Operator_product_grid_adapter extends RecyclerView.Adapter<Operator_product_grid_adapter.AppViewholder> implements Filterable {
    List<Get_product_response> productList;
    List<Get_product_response> productListAll;
    private String all_product_discount;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mListener;
    private double discount, price, offer;

    public Operator_product_grid_adapter(List<Get_product_response> productList, String all_product_discount) {
        this.productList = productList;
        this.productListAll = new ArrayList<>();
        this.productListAll = productList;
        this.all_product_discount = all_product_discount;

    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        mListener = listener;

    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.operator_product_grid_card, parent, false);
        return new Operator_product_grid_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        Get_product_response product = productList.get(position);
        double selling_price=Double.parseDouble(product.getSelling_price());
        double discount=Double.parseDouble(product.getProduct_offer());
    if(Double.parseDouble(all_product_discount)>discount)
        {
            discount=Double.parseDouble(all_product_discount);
        }
        double price_with_offer=selling_price-selling_price*(discount/100);
        price = Double.parseDouble(productList.get(position).getSelling_price());
        offer = Double.parseDouble(productList.get(position).getProduct_offer());

        holder.product_name.setText(productList.get(position).getProduct_name());

        ImageHelper.imageLoader(holder.itemView.getContext(),  holder.product_image, product.getProduct_image());
        holder.product_price.setText(new DecimalFormat("##.##").format(price));

        if (offer == 0) {
            holder.discountLayout.setVisibility(View.INVISIBLE);
        } else {
            holder.product_discount.setText(new DecimalFormat("##.##").format(price_with_offer));
            holder.product_price.setPaintFlags(holder.product_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Get_product_response> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filterList.addAll(productListAll);
            } else {
                for (Get_product_response product_response : productListAll) {
                    if ((product_response.getProduct_name().toLowerCase().contains(constraint.toString().toLowerCase())) || (product_response.getBrand().toLowerCase().contains(constraint.toString().toLowerCase()))) {
                        filterList.add(product_response);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productList.clear();
            productList.addAll((Collection<? extends Get_product_response>) results.values);
            notifyDataSetChanged();
        }
    };


    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView product_name, product_price, product_unit, product_discount;
        ImageView product_image;
        CheckBox check;
        LinearLayout discountLayout;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.productlabelID);
            product_image = itemView.findViewById(R.id.productImage);
            product_price = (TextView) itemView.findViewById(R.id.priceID);
            product_discount = (TextView) itemView.findViewById(R.id.discountID);
            discountLayout = (LinearLayout) itemView.findViewById(R.id.discountLayoutID);
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
