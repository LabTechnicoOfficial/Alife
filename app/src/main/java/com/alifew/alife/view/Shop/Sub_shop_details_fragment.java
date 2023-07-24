package com.alifew.alife.view.Shop;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alifew.alife.R;
import com.squareup.picasso.Picasso;

public class Sub_shop_details_fragment extends Fragment {
    ImageView shopImage;
    TextView shopName, shopLocation;
    String subShop_id, subShop_name, subShop_location, subShop_image;

    LinearLayout categoryLayout, productsLayout, operatorsLayout, customersLayout;
    FragmentManager fragmentManager;


    public Sub_shop_details_fragment(String subShop_id, String subShop_name, String subShop_location, String subShop_image) {
        this.subShop_id = subShop_id;
        this.subShop_name = subShop_name;
        this.subShop_location = subShop_location;
        this.subShop_image = subShop_image;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_shop_details_fragment, container, false);
        checkConnection();

        shopImage = (ImageView) view.findViewById(R.id.subShopImageID);
        shopName = (TextView) view.findViewById(R.id.subShopNameID);
        shopLocation = (TextView) view.findViewById(R.id.subShopLocationID);

        categoryLayout = (LinearLayout) view.findViewById(R.id.categoriesLayoutID);
        productsLayout = (LinearLayout) view.findViewById(R.id.productsLayoutID);
        operatorsLayout = (LinearLayout) view.findViewById(R.id.operatorsLayoutID);
        customersLayout = (LinearLayout) view.findViewById(R.id.customersLayoutID);

        fragmentManager = getFragmentManager();

        shopName.setText(subShop_name);
        shopLocation.setText(subShop_location);
        Picasso.get().load(subShop_image).into(shopImage);

        categoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_categories_fragment(subShop_id)).addToBackStack(null).commit();

            }
        });


        productsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_all_products_fragment(subShop_id)).addToBackStack(null).commit();
            }

        });

        operatorsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_admin_fragments(subShop_id)).addToBackStack(null).commit();
            }
        });

        customersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Shop_customer_list_fragments(subShop_id)).addToBackStack(null).commit();
            }
        });

        return view;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void checkConnection() {
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

    private void refreshFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
    }
}