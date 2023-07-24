package com.alifew.alife.adapter.Operator;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.R;
import com.alifew.alife.model.Get_product_response;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Operator_product_gridOff_adapter extends RecyclerView.Adapter<Operator_product_gridOff_adapter.AppViewholder> implements Filterable {
    List<Get_product_response> productList;
    List<Get_product_response> productListAll;
    private String all_product_discount;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mListener;
    private OnItemTypeListener mListener5;
    private OnItemOfferListener mListener6;


    public Operator_product_gridOff_adapter(List<Get_product_response> productList, String all_product_discount) {
        this.productList = productList;
        this.productListAll = new ArrayList<>();
        this.productListAll = productList;
        this.all_product_discount=all_product_discount;

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

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.operator_product_gridoff_card, parent, false);
        return new Operator_product_gridOff_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        Get_product_response product = productList.get(position);
        // holder.category.setText(category.getCatagory01y_name());
        holder.id.setText(productList.get(position).getProduct_id());
        holder.name.setText(productList.get(position).getProduct_name());
        Double total_buy_price = Double.parseDouble(productList.get(position).getBuy_price()) * Double.parseDouble(productList.get(position).getStock_amount());

        // holder.buy_price.setText(productList.get(position).getBuy_price());
        holder.buy_price.setText(String.valueOf(new DecimalFormat("##.##").format(total_buy_price)));
        holder.sell_profit.setText(productList.get(position).getSell_profit() + "%");
        holder.stock.setText(productList.get(position).getStock_amount());
        double buy_price=Double.parseDouble(product.getBuy_price());
        double stock_amount=Double.parseDouble(productList.get(position).getStock_amount());
        double selling_price=Double.parseDouble(product.getSelling_price());
        double discount=Double.parseDouble(product.getProduct_offer());
        if(Double.parseDouble(all_product_discount)>discount)
        {
            discount=Double.parseDouble(all_product_discount);
        }
        double selling_price_with_discount=selling_price+selling_price*(discount/100);
        double total_selling_price_with_discount=selling_price_with_discount*stock_amount;
        double total_profit=total_selling_price_with_discount-total_buy_price;
        holder.sell_price.setText(String.valueOf(total_selling_price_with_discount));
        holder.discount.setText(productList.get(position).getProduct_offer() + "%");
        holder.profit.setText(String.valueOf(total_profit));
        holder.added_by.setText(product.getAdded_by());
        if (!(TextUtils.isEmpty(productList.get(position).getProduct_added_date()))) {
            holder.added_date.setText(productList.get(position).getProduct_added_date());
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public interface OnItemTypeListener {
        void OnItemType(int position);
    }

    public interface OnItemOfferListener {
        void OnItemOffer(int position);
    }
    public void setOnClickListener(OnItemClickListener listener, OnItemTypeListener Listener5, OnItemOfferListener Listener6) {
        this.mListener = listener;

        this.mListener5 = Listener5;
        this.mListener6 = Listener6;

    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        private TextView showHideButton, typeOpen, offerOpen;
        TextView id, name, buy_price, sell_profit, sell_price, stock, discount, sellwithdiscount_price, added_date, profit, profit_discount, added_by, delete;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.productIDID);
            name = itemView.findViewById(R.id.productNameID);
            buy_price = itemView.findViewById(R.id.buyPriceID);
            sell_profit = itemView.findViewById(R.id.sellPercentageID);
            sell_price = itemView.findViewById(R.id.sellPriceID);
            stock = itemView.findViewById(R.id.productStockID);
            discount = itemView.findViewById(R.id.discountID);
            profit = itemView.findViewById(R.id.profitID);
            added_date = itemView.findViewById(R.id.addDateID);
            delete = itemView.findViewById(R.id.deleteID);
            showHideButton = (TextView) itemView.findViewById(R.id.showHideButtonID);
            typeOpen = (TextView) itemView.findViewById(R.id.typeOpenID);
            offerOpen = (TextView) itemView.findViewById(R.id.offerOpenID);
            added_by = (TextView) itemView.findViewById(R.id.addedBy);
            typeOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener5 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener5.OnItemType(position);
                        }
                    }
                }
            });

            offerOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener6 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener6.OnItemOffer(position);
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
