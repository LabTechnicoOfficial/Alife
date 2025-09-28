package com.alifew.bcopay.view.Shop;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alifew.bcopay.R;
import com.alifew.bcopay.adapter.Shop_offer_all_products_adapter;
import com.alifew.bcopay.model.add_shop_all_product_offer_response;
import com.alifew.bcopay.model.delete_shop_all_product_offer_response;
import com.alifew.bcopay.model.get_shop_all_product_offer_response;
import com.alifew.bcopay.viewmodel.Shop_all_product_offer;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class Shop_offer_allProducts_fragment extends Fragment implements Shop_offer_all_products_adapter.OnItemDeletrListener {

    String shopID;
    ExtendedFloatingActionButton addOfferButton;
    RecyclerView offersView;
    Shop_all_product_offer shop_offer;
    private Shop_offer_all_products_adapter adapter;
    private List<get_shop_all_product_offer_response> offerList;
    get_shop_all_product_offer_response offer_response;

    public Shop_offer_allProducts_fragment(String shopID) {
        this.shopID = shopID;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();

        addOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog addOfferAlert = new Dialog(getActivity());
                addOfferAlert.setContentView(R.layout.shop_offer_all_products_alert);
                addOfferAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                addOfferAlert.setCancelable(false);
                addOfferAlert.show();

                ImageView closeButton = addOfferAlert.findViewById(R.id.closeID);
                TextInputLayout amountError = addOfferAlert.findViewById(R.id.amountErrorID);
                TextInputLayout priceError = addOfferAlert.findViewById(R.id.priceErrorID);
                TextInputLayout offerError = addOfferAlert.findViewById(R.id.offerErrorID);

                TextInputEditText amountText = addOfferAlert.findViewById(R.id.amountText);
                TextInputEditText priceText = addOfferAlert.findViewById(R.id.priceTextID);
                TextInputEditText offerText = addOfferAlert.findViewById(R.id.offerTextID);

                AppCompatButton addButton = addOfferAlert.findViewById(R.id.addButton);

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo info = manager.getActiveNetworkInfo();
                        if (info == null) {
                            Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                        }else {
                            String amount = amountText.getText().toString().trim();
                            String price = priceText.getText().toString().trim();
                            String offer = offerText.getText().toString().trim();

                            amountError.setErrorEnabled(false);
                            priceError.setErrorEnabled(false);
                            offerError.setErrorEnabled(false);

                            if (TextUtils.isEmpty(amount) || TextUtils.isEmpty(price) || TextUtils.isEmpty(offer)) {

                                if (TextUtils.isEmpty(amount)) {
                                    amountError.setError(" ");
                                } else if (TextUtils.isEmpty(price)) {
                                    priceError.setError(" ");
                                } else if (TextUtils.isEmpty(offer)) {
                                    offerError.setError(" ");
                                }
                            } else {
                                shop_offer.add_offer(amount, price, offer, shopID).observe(getViewLifecycleOwner(), new Observer<add_shop_all_product_offer_response>() {
                                    @Override
                                    public void onChanged(add_shop_all_product_offer_response add_shop_all_product_offer_response) {
                                        if (add_shop_all_product_offer_response.getMessage().equals("yess")) {
                                            Toast.makeText(getActivity(), "Successfully Added", Toast.LENGTH_SHORT).show();
                                            addOfferAlert.dismiss();
                                            main();
                                        } else {
                                            Toast.makeText(getActivity(), "Fail to Added", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                                //code
                            }
                        }

                    }
                });

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addOfferAlert.dismiss();
                    }
                });
            }
        });
    }

    private void main() {
        offerList = new ArrayList<>();
        shop_offer = new ViewModelProvider(getActivity()).get(Shop_all_product_offer.class);
        shop_offer.get_offer(shopID).observe(getViewLifecycleOwner(), new Observer<List<get_shop_all_product_offer_response>>() {
            @Override
            public void onChanged(List<get_shop_all_product_offer_response> get_shop_all_product_offer_responses) {
                offerList = get_shop_all_product_offer_responses;

                adapter = new Shop_offer_all_products_adapter(offerList);
                adapter.setOnItemClickListener(Shop_offer_allProducts_fragment.this::OnItemDelete);
                offersView.setAdapter(adapter);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_offer_allproducts_fragment, container, false);

        addOfferButton = view.findViewById(R.id.offersButtonID);
        offersView = view.findViewById(R.id.offersViewID);
        offersView.setHasFixedSize(true);
        offersView.setLayoutManager(new LinearLayoutManager(getContext()));

        offersView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && addOfferButton.getVisibility() == View.VISIBLE) {
                    addOfferButton.hide();
                } else if (dy < 0 && addOfferButton.getVisibility() != View.VISIBLE) {
                    addOfferButton.show();
                }
            }
        });

        return view;
    }

    @Override
    public void OnItemDelete(int position) {
        get_shop_all_product_offer_response offer = offerList.get(position);
        String offer_id = offer.getId();
        Dialog alert = new Dialog(getActivity());
        alert.setContentView(R.layout.confirm_alert);
        alert.show();

        TextView yesButton = alert.findViewById(R.id.yesButton);
        TextView noButton = alert.findViewById(R.id.noButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shop_offer.delete_offer(offer_id).observe(getViewLifecycleOwner(), new Observer<delete_shop_all_product_offer_response>() {
                    @Override
                    public void onChanged(delete_shop_all_product_offer_response delete_shop_all_product_offer_response) {
                        if (delete_shop_all_product_offer_response.getMessage().equals("deleted successfully")) {
                            Toast.makeText(getActivity(), "Delete Successfully", Toast.LENGTH_SHORT).show();
                            alert.dismiss();
                            main();
                        } else {
                            Toast.makeText(getActivity(), "Fail to Delete.Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                main();
            }
        });
    }
}