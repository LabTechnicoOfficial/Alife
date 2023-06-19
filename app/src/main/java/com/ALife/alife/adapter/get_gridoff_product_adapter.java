package com.ALife.alife.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.Utils.Helpers;
import com.ALife.alife.model.get_product_response;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class get_gridoff_product_adapter extends RecyclerView.Adapter<get_gridoff_product_adapter.AppViewholder> implements Filterable {
    List<get_product_response> productList;
    List<get_product_response> productListAll;
    private String all_product_discount;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mListener;
    private OnItemDeleteListener mListener3;
    private OnItemHideListener mListener4;
    private OnItemTypeListener mListener5;
    private OnItemOfferListener mListener6;
    private OnItemSellListener sListener;
    private TextView showHideButton, typeOpen, offerOpen, sellButton;


    public get_gridoff_product_adapter(List<get_product_response> productList, String all_product_discount) {
        this.productList = productList;
        this.productListAll = new ArrayList<>();
        this.productListAll = productList;
        this.all_product_discount = all_product_discount;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<get_product_response> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filterList.addAll(productListAll);
            } else {
                for (get_product_response product_response : productListAll) {
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
            productList.addAll((Collection<? extends get_product_response>) results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public interface OnItemDeleteListener {
        void OnItemDelete(int position);
    }

    public interface OnItemHideListener {
        void OnItemHide(int position);
    }

    public interface OnItemTypeListener {
        void OnItemType(int position);
    }

    public interface OnItemOfferListener {
        void OnItemOffer(int position);
    }

    public interface OnItemSellListener {
        void OnItemSell(int position);
    }


    public void setOnClickListener(OnItemClickListener listener, OnItemDeleteListener Listener3, OnItemHideListener Listener4, OnItemTypeListener Listener5, OnItemOfferListener Listener6, OnItemSellListener sListener) {
        mListener = listener;
        mListener3 = Listener3;
        mListener4 = Listener4;
        mListener5 = Listener5;
        mListener6 = Listener6;
        this.sListener = sListener;
    }


    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_off_product_card, parent, false);
        return new AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        get_product_response product = productList.get(position);
        // holder.category.setText(category.getCatagory01y_name());
        holder.id.setText(productList.get(position).getProduct_id());
        holder.name.setText(productList.get(position).getProduct_name());
        Double total_buy_price = Double.parseDouble(productList.get(position).getBuy_price()) * Double.parseDouble(productList.get(position).getStock_amount());

        // holder.buy_price.setText(productList.get(position).getBuy_price());
        holder.buy_price.setText(String.valueOf(new DecimalFormat("##.##").format(total_buy_price)));
        holder.sell_profit.setText(productList.get(position).getSell_profit() + "%");
        holder.stock.setText(productList.get(position).getStock_amount());
        double buy_price = Double.parseDouble(product.getBuy_price());
        double stock_amount = Double.parseDouble(productList.get(position).getStock_amount());
        double selling_price = Double.parseDouble(product.getSelling_price());
        double discount = Double.parseDouble(product.getProduct_offer());
        if (Double.parseDouble(all_product_discount) > discount) {
            discount = Double.parseDouble(all_product_discount);
        }
        double selling_price_with_discount = selling_price - selling_price * (discount / 100);
        double total_selling_price_with_discount = selling_price_with_discount * stock_amount;
        double total_profit = total_selling_price_with_discount - total_buy_price;
        holder.sell_price.setText(String.valueOf(new DecimalFormat("##.##").format(total_selling_price_with_discount)));
        holder.discount.setText(productList.get(position).getProduct_offer() + "%");
        holder.profit.setText(String.valueOf(new DecimalFormat("##.##").format(total_profit)));

        holder.added_by.setText(product.getAdded_by());
        holder.all_discount.setText(all_product_discount + "%");
        if (!(TextUtils.isEmpty(productList.get(position).getProduct_added_date()))) {
            holder.added_date.setText(productList.get(position).getProduct_added_date());
        }

        if (product.getStatus().equals("1")) {
            showHideButton.setText("Hide");
            showHideButton.setTextColor(Color.BLACK);

        } else if (product.getStatus().equals("0")) {
            showHideButton.setText("Show");
            showHideButton.setTextColor(Color.RED);

        }

        holder.brandName.setText(product.getBrand());
        holder.codeText.setText(product.getCode());

        holder.barCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(holder.itemView.getContext(), product.getCode(), Toast.LENGTH_SHORT).show();
                Helpers.barCodeGenerator(holder.itemView.getContext(), product.getCode());
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView id, name, buy_price, sell_profit, sell_price;
        TextView stock, discount, sellwithdiscount_price, added_date;
        TextView profit, profit_discount, added_by, delete, all_discount;
        TextView brandName, codeText;
        ImageView barCodeButton;


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
            all_discount = (TextView) itemView.findViewById(R.id.allDiscountID);
            brandName = itemView.findViewById(R.id.brandTextID);
            codeText = itemView.findViewById(R.id.codeTextID);
            barCodeButton = itemView.findViewById(R.id.barCodeButton);


            delete.setOnClickListener(new View.OnClickListener() {
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

            showHideButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener4 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener4.OnItemHide(position);
                        }
                    }
                }
            });

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
