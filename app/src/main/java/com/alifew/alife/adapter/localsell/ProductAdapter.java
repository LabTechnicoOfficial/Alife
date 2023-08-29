package com.alifew.alife.adapter.localsell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alifew.alife.DB.entity.Customer;
import com.alifew.alife.DB.entity.LocalSellProducts;
import com.alifew.alife.R;
import com.alifew.alife.Utils.ImageHelper;
import com.alifew.alife.view.Shop.Shop_local_sell_fragment;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<LocalSellProducts> {
    List<LocalSellProducts> productsList;
    Shop_local_sell_fragment fragment;

    public ProductAdapter(@NonNull Context context, @NonNull List<LocalSellProducts> productsList, Shop_local_sell_fragment shopLocalSellFragment) {
        super(context, 0, productsList);

        this.productsList = productsList;
        this.fragment = shopLocalSellFragment;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return userFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.local_sell_select_product_card, parent, false);
        }


        TextView product_name = convertView.findViewById(R.id.productlabelID);
        ImageView product_image = convertView.findViewById(R.id.productImage);
        TextView product_price = convertView.findViewById(R.id.priceID);
        TextView buyPriceText = convertView.findViewById(R.id.buyPriceText);

        LocalSellProducts products = getItem(position);

        assert products != null;
        product_name.setText(products.getName());
        product_price.setText(convertView.getContext().getResources().getString(R.string.price) + ": " + products.getSellPrice());
        buyPriceText.setText(convertView.getContext().getResources().getString(R.string.buy_price)+": "+products.getBuyPrice());
        ImageHelper.imageLoader(convertView.getContext(), product_image, products.getImage());

        return convertView;
    }

    private final Filter userFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            List<LocalSellProducts> suggestions = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                suggestions.addAll(productsList);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (LocalSellProducts products : productsList) {
                    if (products.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(products);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List) filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            LocalSellProducts products = (LocalSellProducts) resultValue;
            fragment.buyPrice = Double.parseDouble(products.getBuyPrice());
            fragment.productPrice = Double.parseDouble(products.getSellPrice());
//            fragment.productPriceText.setText(products.getSellPrice());
//            fragment.buyPriceText.setText(products.getBuyPrice());
            fragment.imageList.add(products.getImage());
            //fragment.setUIValue(products.getName(), products.getSellPrice(), products.getBuyPrice());
            return products.getName();
        }
    };
}
