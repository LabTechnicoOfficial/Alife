package com.alifew.alife.view.Shop;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.alife.Custom_Type.ProductSell;
import com.alifew.alife.R;
import com.alifew.alife.adapter.Selected_sell_product_list_adapter;
import com.alifew.alife.model.Get_product_response;
import com.alifew.alife.model.get_product_type_response;
import com.alifew.alife.viewmodel.Get_product;
import com.alifew.alife.viewmodel.Get_product_type;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Shop_sell_checkout_fragment extends Fragment implements Selected_sell_product_list_adapter.OnItemAddListener, Selected_sell_product_list_adapter.OnItemMinusListener, Selected_sell_product_list_adapter.OnItemRemoveListener {

    String shop_id, customer_id, customer_image, customer_name, customer_phone, customer_location;
    private List<ProductSell> productsList;
    CircularImageView customerImage;
    TextView customerName, customerLocation, customerPhone, customerID, totalPrice;
    LinearLayout customerIDLayout;
    private Selected_sell_product_list_adapter adapter;
    RecyclerView productView;
    LinearLayoutManager layoutManager;
    private double price_total;
    private Double stock;
    Get_product get_product;
    private Get_product_type get_product_type;
    private Double type_amount_check;

    public Shop_sell_checkout_fragment(String shop_id, String customer_id, String customer_image, String customer_name, String customer_phone, String customer_location, List<ProductSell> productsList) {
        this.shop_id = shop_id;
        this.customer_id = customer_id;
        this.customer_image = customer_image;
        this.customer_name = customer_name;
        this.customer_phone = customer_phone;
        this.customer_location = customer_location;
        this.productsList = productsList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();
    }

    private void main() {
        checkConnection();
        set_customer();
        show_cart();

    }

    public void get_product_type_count(String productId, ProductSell product, Double amount) {
        get_product_type = new ViewModelProvider(getActivity()).get(Get_product_type.class);
        get_product_type.getdata(productId).observe(getViewLifecycleOwner(), new Observer<List<get_product_type_response>>() {
            @Override
            public void onChanged(List<get_product_type_response> get_product_type_responses) {
                for (int i = 0; i < get_product_type_responses.size(); i++) {
                    if (amount < Double.parseDouble(get_product_type_responses.get(i).getCount())) {
                        product.setAmount(String.valueOf(Double.parseDouble(product.getAmount()) + 1));
                        product.setPrice(String.valueOf(Double.parseDouble(product.getAmount()) * Double.parseDouble(product.getUnit_price())));
                        adapter.notifyDataSetChanged();
                        setTotalPrice();
                    } else {
                        Toast.makeText(getActivity(), "amount overflow", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void get_product_stock(String productId, ProductSell product, Double amount) {
        stock = 0.0;
        get_product = new ViewModelProvider(getActivity()).get(Get_product.class);
        get_product.getsingle_product(productId).observe(getViewLifecycleOwner(), new Observer<Get_product_response>() {
            @Override
            public void onChanged(Get_product_response get_product_response) {
                stock = Double.parseDouble(get_product_response.getStock_amount());
                if (stock <= amount) {
                    Toast.makeText(getActivity(), "amount overflow", Toast.LENGTH_SHORT).show();
                } else {
                    if (product.getType_id().equals("0")) {
                        product.setAmount(String.valueOf(Double.parseDouble(product.getAmount()) + 1));
                        product.setPrice(String.valueOf(Double.parseDouble(product.getAmount()) * Double.parseDouble(product.getUnit_price())));
                        adapter.notifyDataSetChanged();
                        setTotalPrice();
                    } else {
                        type_amount_check = 0.0;
                        for (int i = 0; i < productsList.size(); i++) {
                            if (product.getType_id().equals(productsList.get(i).getType_id())) {
                                type_amount_check += Double.parseDouble(product.getAmount());
                            }
                        }
                        get_product_type_count(productId, product, type_amount_check);
                    }
                }
            }
        });

    }

    public void show_cart() {
        adapter = new Selected_sell_product_list_adapter(productsList, getActivity(),2);
        adapter.setOnClickListener(Shop_sell_checkout_fragment.this::OnItemAdd, Shop_sell_checkout_fragment.this::OnItemMinus, Shop_sell_checkout_fragment.this::OnItemRemove);
        productView.setAdapter(adapter);
        setTotalPrice();

    }

    public void setTotalPrice() {

        if (productsList.size() > 0) {
            price_total = 0.0;
            for (int i = 0; i < productsList.size(); i++) {
                price_total += Double.parseDouble(productsList.get(i).getPrice());
            }
            totalPrice.setText("Total Price: " + String.valueOf(price_total));
        } else {
            totalPrice.setText("");
        }
    }

    public void set_customer() {
        if (!customer_image.equals("blank")) {
            Picasso.get().load(customer_image).into(customerImage);
        } else {
            customerImage.setVisibility(View.GONE);
        }

        if (!customer_id.equals("0")) {
            customerID.setText(customer_id);
        } else {
            customerIDLayout.setVisibility(View.GONE);
        }
        customerName.setText(customer_name);
        customerPhone.setText(customer_phone);
        customerLocation.setText(customer_location);
    }

    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        Dialog networkAlert = new Dialog(getActivity());
        networkAlert.setContentView(R.layout.network_alert);
        networkAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView connectButton = (TextView) networkAlert.findViewById(R.id.connectButtonID);
        if (info == null) {
            networkAlert.show();
            connectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    networkAlert.dismiss();

                    refreshFragment();
                }
            });
        }
    }

    public void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
        //adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_sell_checkout_fragment, container, false);

        customerImage = (CircularImageView) view.findViewById(R.id.customerImageID);
        customerID = (TextView) view.findViewById(R.id.customerid_ID);
        customerName = (TextView) view.findViewById(R.id.customerNameID);
        customerPhone = (TextView) view.findViewById(R.id.customerPhoneID);
        customerLocation = (TextView) view.findViewById(R.id.customerLocationID);
        totalPrice = (TextView) view.findViewById(R.id.total_price);
        customerIDLayout = (LinearLayout) view.findViewById(R.id.customerIDLayoutID);
        productView = (RecyclerView) view.findViewById(R.id.productsViewID);
        layoutManager = new LinearLayoutManager(getContext());
        productView.setHasFixedSize(true);
        productView.setLayoutManager(layoutManager);


        return view;
    }

    @Override
    public void OnItemAdd(int position) {
        ProductSell product = productsList.get(position);
        String selected_productId = product.getProduct_id();
        Double amount = 0.0;
        for (int i = 0; i < productsList.size(); i++) {
            if (productsList.get(i).getProduct_id().equals(selected_productId)) {
                amount += Double.parseDouble(productsList.get(i).getAmount());
            }
        }
        get_product_stock(selected_productId, product, amount);


    }

    @Override
    public void OnItemMinus(int position) {
        ProductSell product = productsList.get(position);
        String sell_amount = product.getAmount();
        String unit_price = product.getUnit_price();
        if (Double.parseDouble(sell_amount) > 0) {
            product.setAmount(String.valueOf(Double.parseDouble(sell_amount) - 1));
            product.setPrice(String.valueOf(Double.parseDouble(product.getAmount()) * Double.parseDouble(unit_price)));
        }
        adapter.notifyDataSetChanged();
        setTotalPrice();
    }

    @Override
    public void OnItemRemove(int position) {
        productsList.remove(position);
        adapter.notifyDataSetChanged();
        setTotalPrice();
    }
}